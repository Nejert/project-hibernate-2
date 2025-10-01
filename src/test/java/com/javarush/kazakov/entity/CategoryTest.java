package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CategoryTest {
    @Test
    public void getCategoryTest() {
        int categoryId = 1;
        String expected = "Action";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Category category = session.find(Category.class, categoryId);
            log.debug(category.getName());
            Assertions.assertNotNull(category);
            Assertions.assertEquals(expected, category.getName());
        }
    }

    @Test
    public void getFilmsByCategory() {
        int categoryId = 6;
        String expected = "ACADEMY DINOSAUR,ADAPTATION HOLES,ARMY FLINTSTONES";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Category category = session.find(Category.class, categoryId);
            List<Film> films = category.getFilms();
            String filmsStr = films.stream()
                    .map(Film::getFilmText)
                    .map(FilmText::getTitle)
                    .collect(Collectors.joining(","));
            log.debug("Category: '{}' Films: '{}'", category.getName(), filmsStr);
            Assertions.assertNotNull(films);
            Assertions.assertTrue(filmsStr.contains(expected));
        }
    }
}
