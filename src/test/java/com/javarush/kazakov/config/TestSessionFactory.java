package com.javarush.kazakov.config;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.lang.reflect.Field;
import java.util.Properties;


public class TestSessionFactory {
    private static org.hibernate.SessionFactory sessionFactory;

    @SneakyThrows
    private TestSessionFactory(Properties extraProperties) {
        org.hibernate.SessionFactory standardFactory = SessionFactory.getSessionFactory();
        Field configConst = SessionFactory.class.getDeclaredField("CONFIGURATION");
        if (configConst.trySetAccessible()) {
            Configuration config = (Configuration) configConst.get(standardFactory);
            config.addProperties(extraProperties);
            sessionFactory = config.buildSessionFactory();
            standardFactory.close();
        }
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties extraProperties = new Properties();
            extraProperties.put(Environment.JAKARTA_JDBC_URL, "jdbc:p6spy:mysql://localhost:3306/test");
            extraProperties.put(Environment.HBM2DDL_AUTO, "create");
            new TestSessionFactory(extraProperties);
        }
        return sessionFactory;
    }

    public static org.hibernate.SessionFactory getSessionFactory(Properties extraProperties) {
        if (sessionFactory == null) {
            new TestSessionFactory(extraProperties);
        }
        return sessionFactory;
    }
//    public static void main(String[] args) {
//        try (Session session = getSessionFactory().openSession()) {
//        }
//    }
}
