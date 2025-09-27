package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import com.javarush.kazakov.entity.misc.Rating;
import com.javarush.kazakov.entity.misc.SpecialFeature;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
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

    @Test
    public void filmLanguageIdentityTest() {
        int filmId1 = 1;
        int filmId2 = 2;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film1 = session.find(Film.class, filmId1);
            Film film2 = session.find(Film.class, filmId2);
            log.debug("Film 1: {}", film1.toString());
            log.debug("Film 2: {}", film2.toString());
            Assertions.assertNotNull(film1.getLanguage());
            Assertions.assertNotNull(film2.getLanguage());
            Assertions.assertSame(film1.getLanguage(), film2.getLanguage());
        }
    }

    @Test
    public void getCategoriesByFilmTest() {
        int filmId = 1;
        String expected = "Documentary";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            List<Category> categories = film.getCategories();
            String categoriesStr = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(","));
            log.debug("Film: '{}' Categories: '{}'", film.getTitle(), categoriesStr);
            Assertions.assertNotNull(categories);
            Assertions.assertEquals(expected, categoriesStr);
        }
    }

    @Test
    public void getActorsByFilmTest() {
        int filmId = 1;
        String expected = "PENELOPE GUINESS,CHRISTIAN GABLE,LUCILLE TRACY";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Film film = session.find(Film.class, filmId);
            List<Actor> actors = film.getActors();
            String actorsStr = actors.stream()
                    .map(e -> e.getFirstName() + " " + e.getLastName())
                    .collect(Collectors.joining(","));
            log.debug("Film: '{}' Actors: '{}'", film.getTitle(), actorsStr);
            Assertions.assertNotNull(actors);
            Assertions.assertTrue(actorsStr.contains(expected));
        }
    }
}
