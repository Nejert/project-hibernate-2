package com.javarush.kazakov;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionFactoryTest {
    @Test
    public void getSessionAndDataTest() {
        Integer result = null;
        int expectedRows = 1000;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            result = session.createNativeQuery("SELECT count(*) FROM film", Integer.class).uniqueResult();
        }
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedRows, result);
    }
}
