package com.javarush.kazakov.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.util.Properties;

public class Container {
    public static final MySQLContainer<?> CONTAINER;
    public static final String DOCKER_IMAGE_NAME = "mysql:8.0.36";
    public static final SessionFactory SESSION_FACTORY;
    private static final String TEST_SCHEMA = "movie";
    private static final String TEST_USER = "root";
    private static final String TEST_PASSWORD = "root";
    private static final int PORT;

    static {
        CONTAINER = new MySQLContainer<>(DOCKER_IMAGE_NAME)
                .withDatabaseName(TEST_SCHEMA)
                .withUsername(TEST_USER)
                .withPassword(TEST_PASSWORD)
                .withCopyFileToContainer(
                        MountableFile.forClasspathResource(
                                "/dump-hibernate-2.sql"), "/home/"
                );
        CONTAINER.start();
        try {
            org.testcontainers.containers.Container.ExecResult mysql =
                    CONTAINER.execInContainer("mysql",
                            "--user=%s".formatted(TEST_USER),
                            "--password=%s".formatted(TEST_PASSWORD),
                            "--database=%s".formatted(TEST_SCHEMA),
                            "-e", "source /home/dump-hibernate-2.sql");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        PORT = CONTAINER.getFirstMappedPort();
        SESSION_FACTORY = initContainerSessionFactory();
        int i = 0;
    }

    private static SessionFactory initContainerSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty(Environment.JAKARTA_JDBC_URL, CONTAINER.getJdbcUrl().replace("jdbc:", "jdbc:p6spy:"));
        properties.setProperty(Environment.JAKARTA_JDBC_USER, CONTAINER.getUsername());
        properties.setProperty(Environment.JAKARTA_JDBC_PASSWORD, CONTAINER.getPassword());
        properties.put(Environment.HBM2DDL_AUTO, "none");
        return TestSessionFactory.getSessionFactory(properties);
    }

}
