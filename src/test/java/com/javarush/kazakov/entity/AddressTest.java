package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {
    @Test
    public void getAddressTest() {
        int addressId = 1;
        String expectedAddress = "47 MySakila Drive";
        String expectedCityName = "Lethbridge";
        String expectedCountry = "Canada";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Address address = session.find(Address.class, addressId);
            Assertions.assertNotNull(address);
            Assertions.assertEquals(expectedAddress, address.getAddress());
            Assertions.assertEquals(expectedCityName, address.getCity().getCity());
            Assertions.assertEquals(expectedCountry, address.getCity().getCountry().getCountry());
        }
    }
}
