package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CityTest {
    @Test
    public void getCityTest() {
        int cityId = 1;
        String expectedCityName = "A Corua (La Corua)";
        String expectedCountry = "Spain";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            City city = session.find(City.class, cityId);
            Assertions.assertNotNull(city);
            Assertions.assertEquals(expectedCityName, city.getCity());
            Assertions.assertEquals(expectedCountry, city.getCountry().getCountry());
        }
    }
}
