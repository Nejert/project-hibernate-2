package com.javarush.kazakov;

import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class SessionFactory {
    private static SessionFactory instance;
    private final org.hibernate.SessionFactory sessionFactory;
    private SessionFactory() {
        Properties properties = new Properties();
        try {
            properties.load(SessionFactory.class.getResourceAsStream("/hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sessionFactory = new Configuration()
                .setProperties(properties)
//                .addAnnotatedClass(Film.class)
                .buildSessionFactory();
    }
    public static org.hibernate.SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance.sessionFactory;
    }
}
