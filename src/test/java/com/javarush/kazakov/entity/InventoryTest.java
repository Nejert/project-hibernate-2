package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class InventoryTest {
    @Test
    public void getInventoryTest() {
        int inventoryId = 1;
        String expectedFilmTitle = "ACADEMY DINOSAUR";
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Inventory inventory = session.find(Inventory.class, inventoryId);
            Assertions.assertNotNull(inventory);
            Assertions.assertEquals(expectedFilmTitle, inventory.getFilm().getFilmText().getTitle());
        }
    }
}
