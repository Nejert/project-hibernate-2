package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoreTest {
    @Test
    public void getStoreTest() {
        int storeId = 1;
        int expectedManagerStaffId = 1;
        int expectedAddressId = 1;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Store store = session.find(Store.class, storeId);
            Staff staff = session.find(Staff.class, expectedManagerStaffId);
            Address address = session.find(Address.class, expectedAddressId);
            Assertions.assertNotNull(store);
            Assertions.assertSame(store.getManagerStaff(), staff);
            Assertions.assertSame(store.getAddress(), address);
        }
    }
}
