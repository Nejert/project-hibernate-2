package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import com.p6spy.engine.spy.appender.StdoutLogger;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class FilmTest {
    @Test
    public void getFilmTest() {
        int filmId = 1;
        String title = "ACADEMY DINOSAUR";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            log.debug(film.toString());
            Assertions.assertNotNull(film);
            Assertions.assertEquals(title, film.getTitle());
        }
    }

    @Test
    public void getFilmRatingEnumValueTest() {
        int filmId = 3;
        Rating expected = Rating.NC_17;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            log.debug(film.toString());
            Assertions.assertNotNull(film);
            Assertions.assertEquals(expected, film.getRating());
        }
    }

    @Test
    public void filmSpecialFeatureTest() {
        int filmId = 14;
        Set<SpecialFeature> specialFeatures =
                Set.of(SpecialFeature.TRAILERS, SpecialFeature.DELETED_SCENES, SpecialFeature.BEHIND_THE_SCENES);
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            log.debug(film.toString());
            Assertions.assertNotNull(film);
            Assertions.assertEquals(specialFeatures, film.getSpecialFeatures());
        }
    }

    @Test
    public void filmSpecialFeatureSetTest() {
        int filmId = 1;
        Set<SpecialFeature> specialFeatures =
                Set.of(SpecialFeature.COMMENTARIES, SpecialFeature.TRAILERS, SpecialFeature.DELETED_SCENES,
                        SpecialFeature.BEHIND_THE_SCENES);
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            film.setSpecialFeatures(specialFeatures);
            Field field = null;
            String specialFeaturesString = null;
            try {
                field = film.getClass().getDeclaredField("specialFeaturesString");
                if (field.trySetAccessible()) {
                    specialFeaturesString = (String) field.get(film);
                    log.debug("Special features str: {}", specialFeaturesString);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            String expectedSpecialFeaturesStr = specialFeatures.stream()
                    .map(SpecialFeature::getFeature)
                    .collect(Collectors.joining(","));
            Assertions.assertNotNull(specialFeaturesString);
            Assertions.assertEquals(expectedSpecialFeaturesStr, specialFeaturesString);
        }
    }
}
