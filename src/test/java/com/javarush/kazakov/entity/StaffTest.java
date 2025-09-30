package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StaffTest {
    @Test
    public void getStaffTest() {
        int staffId = 1;
        String expectedFirstName = "Mike";
        String expectedLastName = "Hillyer";
        String expectedEmail = "Mike.Hillyer@sakilastaff.com";
        boolean expectedActive = true;
        String expectedUsername = "Mike";
        String expectedPassword = "8cb2237d0679ca88db6464eac60da96345513964";
        int expectedAddressId = 3;
        int expectedStoreId = 1;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Staff staff = session.find(Staff.class, staffId);
            Address expectedAddress = session.find(Address.class, expectedAddressId);
            Store expectedStore = session.find(Store.class, expectedStoreId);
            Assertions.assertNotNull(staff);
            Assertions.assertEquals(expectedFirstName, staff.getFirstName());
            Assertions.assertEquals(expectedLastName, staff.getLastName());
            Assertions.assertEquals(expectedEmail, staff.getEmail());
            Assertions.assertEquals(expectedActive, staff.getActive());
            Assertions.assertEquals(expectedUsername, staff.getUsername());
            Assertions.assertEquals(expectedPassword, staff.getPassword());
            Assertions.assertSame(expectedAddress, staff.getAddress());
            Assertions.assertSame(expectedStore, staff.getStore());
        }
    }
}
