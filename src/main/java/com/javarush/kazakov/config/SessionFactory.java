package com.javarush.kazakov.config;


import jakarta.persistence.Entity;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class SessionFactory {
    private static SessionFactory instance;
    private final org.hibernate.SessionFactory sessionFactory;
    private static final String ENTITY = "entity";
    private static final String EXT = ".class";
    private static final String ENTITY_PACKAGE = "com.javarush.kazakov." + ENTITY;
    private static final Configuration CONFIGURATION = new Configuration();

    private SessionFactory() {
        Properties properties = new Properties();
        try {
            properties.load(SessionFactory.class.getResourceAsStream("/hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CONFIGURATION.setProperties(properties);
        addEntityAnnotatedClasses();
        sessionFactory = CONFIGURATION.buildSessionFactory();
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance.sessionFactory;
    }

    private void addEntityAnnotatedClasses() {
        for (String entityClassName : findEntityClassNames()) {
            try {
                Class<?> entity = Class.forName(entityClassName);
                if (entity.isAnnotationPresent(Entity.class)) {
                    CONFIGURATION.addAnnotatedClass(entity);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> findEntityClassNames() {
        try (Stream<Path> paths = Files.walk(getEntityPath())) {
            return paths.filter(Files::isRegularFile)
                    .filter(i -> i.toString().endsWith(EXT))
                    .map(i -> i.toString().substring(0, i.toString().indexOf(".")))
                    .map(i -> i.substring(i.indexOf(ENTITY) + ENTITY.length()))
                    .map(i -> ENTITY_PACKAGE.concat(i).replace(File.separator, "."))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getEntityPath() {
        try {
            return Path.of(SessionFactory.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .resolve(ENTITY_PACKAGE.replace(".", File.separator));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
