package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PaymentTest {
    @Test
    public void getPaymentTest() {
        int paymentId = 1;
        int expectedCustomerId = 1;
        int expectedStuffId = 1;
        int expectedRentalId = 76;
        double expectedAmount = 2.99;
        LocalDateTime expectedDate = LocalDateTime.parse("2005-05-25T11:30:37");
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Payment payment = session.find(Payment.class, paymentId);
            Customer customer = session.find(Customer.class, expectedCustomerId);
            Staff staff = session.find(Staff.class, expectedStuffId);
            Rental rental = session.find(Rental.class, expectedRentalId);
            Assertions.assertNotNull(payment);
            Assertions.assertEquals(expectedAmount, payment.getAmount());
            Assertions.assertEquals(expectedDate, payment.getPaymentDate());
            Assertions.assertSame(customer, payment.getCustomer());
            Assertions.assertSame(staff, payment.getStaff());
            Assertions.assertSame(rental, payment.getRental());
        }
    }
}
