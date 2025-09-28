package com.javarush.kazakov.config;


import lombok.SneakyThrows;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.lang.reflect.Field;
import java.util.Properties;


public class TestSessionFactory {
    private static org.hibernate.SessionFactory sessionFactory;

    @SneakyThrows
    private TestSessionFactory() {
        sessionFactory = SessionFactory.getSessionFactory();
        Field configConst = SessionFactory.class.getDeclaredField("CONFIGURATION");
        if (configConst.trySetAccessible()) {
            Properties extraProperties = new Properties();
            extraProperties.put(Environment.JAKARTA_JDBC_URL, "jdbc:p6spy:mysql://localhost:3306/test");
            extraProperties.put(Environment.HBM2DDL_AUTO, "create");
            Configuration config = (Configuration) configConst.get(sessionFactory);
            config.addProperties(extraProperties);
            sessionFactory = config.buildSessionFactory();
        }
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            new TestSessionFactory();
        }
        return sessionFactory;
    }

//    public static void main(String[] args) {
//        try (Session session = getSessionFactory().openSession()) {}
//    }
}
