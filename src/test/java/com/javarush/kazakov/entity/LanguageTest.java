package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class LanguageTest {
    @Test
    public void getLanguageTest() {
        int filmId = 1;
        Language expected = new Language();
        expected.setLanguageId(1);
        expected.setName("English");
        expected.setLastUpdate(LocalDateTime.parse("2006-02-15T03:02:19"));
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            Language actual = film.getLanguage();
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(expected.getLanguageId(), actual.getLanguageId());
            Assertions.assertEquals(expected.getName(), actual.getName());
            Assertions.assertEquals(expected.getLastUpdate(), actual.getLastUpdate());
        }
    }
    @Test
    public void getOriginalLanguageTest() {
        int filmId = 1;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            Language actual = film.getOriginalLanguage();
            Assertions.assertNull(actual);
        }
    }
}
