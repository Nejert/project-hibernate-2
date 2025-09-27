package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ActorTest {
    @Test
    public void getActorTest() {
        int actorId = 1;
        String expected = "PENELOPE GUINESS";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Actor actor = session.find(Actor.class, actorId);
            String actorName = "%s %s".formatted(actor.getFirstName(), actor.getLastName());
            log.debug("Actor: {}", actorName);
            Assertions.assertNotNull(actor);
            Assertions.assertEquals(expected, actorName);
        }
    }

    @Test
    public void getFilmsByActor() {
        int actorId = 1;
        String expected = "ACADEMY DINOSAUR,ANACONDA CONFESSIONS,ANGELS LIFE";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Actor actor = session.find(Actor.class, actorId);
            String actorName = "%s %s".formatted(actor.getFirstName(), actor.getLastName());
            List<Film> films = actor.getFilms();
            String filmsStr = films.stream()
                    .map(Film::getTitle)
                    .collect(Collectors.joining(","));
            log.debug("Actor: '{}' Films: '{}'", actorName, filmsStr);
            Assertions.assertNotNull(films);
            Assertions.assertTrue(filmsStr.contains(expected));
        }
    }
}
