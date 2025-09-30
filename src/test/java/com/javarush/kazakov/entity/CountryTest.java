package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CountryTest {
    @Test
    public void getCountryTest() {
        int countryId = 1;
        String expectedCountryName = "Afghanistan";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Country country = session.find(Country.class, countryId);
            Assertions.assertNotNull(country);
            Assertions.assertEquals(expectedCountryName, country.getCountry());
        }
    }
}
