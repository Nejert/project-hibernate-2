package com.javarush.kazakov.config;

import org.hibernate.cfg.Environment;

import java.util.Properties;


public class TestSessionFactory extends SessionFactory {
    private TestSessionFactory() {
        super();
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            new TestSessionFactory();
            Properties extraProperties = new Properties();
            extraProperties.put(Environment.JAKARTA_JDBC_URL, "jdbc:p6spy:mysql://localhost:3306/test");
            extraProperties.put(Environment.HBM2DDL_AUTO, "create");
            CONFIGURATION.addProperties(extraProperties);
            sessionFactory = CONFIGURATION.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static org.hibernate.SessionFactory getSessionFactory(Properties extraProperties) {
        if (sessionFactory == null) {
            new TestSessionFactory();
            CONFIGURATION.addProperties(extraProperties);
            sessionFactory = CONFIGURATION.buildSessionFactory();
        }
        return sessionFactory;
    }
//    public static void main(String[] args) {
//        try (Session session = getSessionFactory().openSession()) {
//        }
//    }
}
