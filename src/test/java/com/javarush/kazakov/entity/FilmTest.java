package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import com.p6spy.engine.spy.appender.StdoutLogger;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FilmTest {
    @Test
    public void getFilmTest() {
        String title = "ACADEMY DINOSAUR";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, 1);
            StdoutLogger stdoutLogger = new StdoutLogger();
            stdoutLogger.logText(film.toString());
            Assertions.assertNotNull(film);
            Assertions.assertEquals(title, film.getTitle());
        }
    }
}
