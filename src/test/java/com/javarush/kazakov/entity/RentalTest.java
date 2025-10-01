package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class RentalTest {
    @Test
    public void getRentalTest() {
        int rentalId = 1;
        LocalDateTime expectedRentalDate = LocalDateTime.parse("2005-05-24T22:53:30");
        LocalDateTime expectedReturnDate = LocalDateTime.parse("2005-05-26T22:04:30");
        int expectedInventoryId = 367;
        int expectedCustomerId = 130;
        int expectedStaffId = 1;
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Rental rental = session.find(Rental.class, rentalId);
            Inventory inventory = session.find(Inventory.class, expectedInventoryId);
            Customer customer = session.find(Customer.class, expectedCustomerId);
            Staff staff = session.find(Staff.class, expectedStaffId);
            Assertions.assertNotNull(rental);
            Assertions.assertEquals(expectedRentalDate, rental.getRentalDate());
            Assertions.assertEquals(expectedReturnDate, rental.getReturnDate());
            Assertions.assertSame(inventory, rental.getInventory());
            Assertions.assertSame(customer, rental.getCustomer());
            Assertions.assertSame(staff, rental.getStaff());
        }
    }
}
