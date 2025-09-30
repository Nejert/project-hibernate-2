package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CustomerTest {
    @Test
    public void getCustomerTest() {
        int customerId = 1;
        String expectedFirstName = "MARY";
        String expectedLastName = "SMITH";
        String expectedEmail = "MARY.SMITH@sakilacustomer.org";
        boolean expectedActive = true;
        LocalDateTime expectedCreateDate = LocalDateTime.parse("2006-02-14T22:04:36");
        LocalDateTime expectedLastUpdate = LocalDateTime.parse("2006-02-15T02:57:20");
        int expectedAddressId = 5;
        int expectedStoreId = 1;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Customer customer = session.find(Customer.class, customerId);
            Address address = session.find(Address.class, expectedAddressId);
            Store store = session.find(Store.class, expectedStoreId);
            Assertions.assertNotNull(customer);
            Assertions.assertEquals(expectedFirstName, customer.getFirstName());
            Assertions.assertEquals(expectedLastName, customer.getLastName());
            Assertions.assertEquals(expectedEmail, customer.getEmail());
            Assertions.assertEquals(expectedActive, customer.getActive());
            Assertions.assertEquals(expectedCreateDate, customer.getCreateDate());
            Assertions.assertEquals(expectedLastUpdate, customer.getLastUpdate());
            Assertions.assertSame(address, customer.getAddress());
            Assertions.assertSame(store, customer.getStore());
        }
    }
}
