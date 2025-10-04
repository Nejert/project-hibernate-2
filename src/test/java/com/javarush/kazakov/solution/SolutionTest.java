package com.javarush.kazakov.solution;

import com.javarush.kazakov.config.Container;
import com.javarush.kazakov.entity.*;
import com.javarush.kazakov.entity.misc.Rating;
import com.javarush.kazakov.entity.misc.SpecialFeature;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SolutionTest extends Container {
    /**
     * 6. Transactional method that can create a new customer with all dependent fields.
     */
    @Test
    public void createNewCustomer() {
        //store
        int storeId = 1;
        Store store;
        //city
        City city;
        String cityName = "Bandar Seri Begawan";
        //address
        Address address;
        String addressStr = "Jalan 82";
        String district = "Simpang";
        String postalCode = "BB3319";
        String phone = "+6731234567";
        //customer
        Customer customer;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john_doe@gmail.com";
        boolean active = true;

        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            store = session.find(Store.class, storeId);

            city = session.createQuery("from City c where c.city = :name", City.class)
                    .setParameter("name", cityName)
                    .getSingleResult();

            address = new Address();
            address.setAddress(addressStr);
            address.setDistrict(district);
            address.setCity(city);
            address.setPostal_code(postalCode);
            address.setPhone(phone);
            session.persist(address);

            customer = new Customer();
            customer.setStore(store);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setActive(active);

            session.persist(customer);
            transaction.commit();

            int newCustomerId = 600;
            Customer testCustomer = session
                    .createQuery("select c from Customer c where c.firstName = :first and c.lastName = :last", Customer.class)
                    .setParameter("first", firstName)
                    .setParameter("last", lastName)
                    .getSingleResult();
            Assertions.assertNotNull(testCustomer);
            Assertions.assertEquals(newCustomerId, testCustomer.getCustomerId());
            Assertions.assertEquals(store, testCustomer.getStore());
            Assertions.assertEquals(firstName, testCustomer.getFirstName());
            Assertions.assertEquals(lastName, testCustomer.getLastName());
            Assertions.assertEquals(email, testCustomer.getEmail());
            Assertions.assertEquals(address, testCustomer.getAddress());
            Assertions.assertEquals(active, testCustomer.getActive());
            Assertions.assertNotNull(testCustomer.getCreateDate());
            Assertions.assertNotNull(testCustomer.getLastUpdate());
        }
    }

    /**
     * 7. Transactional method that describes the event "the customer returned a previously rented movie"
     * (any customer and rental event). The movie rating does not recalculate.
     */
    @Test
    public void customerReturnedMovieTest() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            Rental rental = session.createQuery("select r from Rental r where r.returnDate is null", Rental.class)
                    .setMaxResults(1)
                    .getSingleResult();
            rental.setReturnDate(LocalDateTime.now());
            session.persist(rental);
            transaction.commit();

            int expectedFilmId = 445;
            Rental testRental = session.get(Rental.class, rental.getRentalId());
            Assertions.assertNotNull(testRental);
            Assertions.assertNotNull(testRental.getReturnDate());
            Assertions.assertEquals(expectedFilmId, testRental.getInventory().getFilm().getFilmId());
        }
    }

    /**
     * 8. Transactional method that describes the event "The customer went to the store and rented inventory.
     * And also made a payment to the staff" when the movie available for rental.
     * This means either there must be no inventory records in the rental table,
     * or the return_date column of the rental table must be populated for the last rental of this inventory.
     */
    @Test
    public void customerRentedInventory() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            Inventory inventory = session.createQuery("select i from Inventory i " +
                            "where i.inventoryId not in (select r.inventory.inventoryId from Rental r where r.returnDate is not null)",
                    Inventory.class).setMaxResults(1).getSingleResult();

            LocalDateTime rentalDate = LocalDateTime.now();
            Customer customer = session.get(Customer.class, 1);
            Staff staff = inventory.getStore().getManagerStaff();

            Rental rental = new Rental();
            rental.setRentalDate(rentalDate);
            rental.setCustomer(customer);
            rental.setStaff(staff);
            rental.setInventory(inventory);
            session.persist(rental);

            double amount = 0.99;
            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setRental(rental);
            payment.setAmount(amount);
            payment.setPaymentDate(rentalDate);
            session.persist(payment);
            transaction.commit();

            int expectedRentalId = 16050;
            Rental testRental = session.get(Rental.class, expectedRentalId);
            Payment testPayment = session.get(Payment.class, expectedRentalId);
            Assertions.assertNotNull(testRental);
            Assertions.assertNotNull(testPayment);
            Assertions.assertEquals(rental, testRental);
            Assertions.assertEquals(payment, testPayment);
            Assertions.assertEquals(staff, testPayment.getStaff());
            Assertions.assertEquals(customer, testPayment.getCustomer());
            Assertions.assertEquals(amount, testPayment.getAmount());
            Assertions.assertEquals(rentalDate, testPayment.getPaymentDate());
            Assertions.assertEquals(rentalDate, testRental.getRentalDate());
        }
    }

    /**
     * 9. Transactional method that describes the event "a new movie was made and became available for rental".
     */
    @Test
    public void createNewMovieAndMakeRentalAvailable() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            String filmTitle = "Test Film title";
            String filmDescription = "Test Film description, this film is available for rental";
            FilmText filmText = new FilmText();
            filmText.setTitle(filmTitle);
            filmText.setDescription(filmDescription);

            String languageName = "English";
            Language language = session.createQuery("select l from Language l where l.name = :name", Language.class)
                    .setParameter("name", languageName)
                    .getSingleResult();

            int rentalDuration = 1;
            double rentalRate = 0.99;
            int length = 85;
            double replacementCost = 1.99;
            Rating rating = Rating.R;
            Set<SpecialFeature> specialFeatures = Set.of(SpecialFeature.values());

            String action = "Action";
            String animation = "Animation";
            String comedy = "Comedy";
            Category actionCategory = session.createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", action)
                    .getSingleResult();
            Category animationCategory = session.createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", animation)
                    .getSingleResult();
            Category comedyCategory = session.createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", comedy)
                    .getSingleResult();
            List<Category> categories = List.of(actionCategory, animationCategory, comedyCategory);

            List<Actor> actors = session.createQuery("select a from Actor a where a.actorId in (:list)", Actor.class)
                    .setParameter("list", List.of(1, 2, 3))
                    .getResultList();

            LocalDateTime now = LocalDateTime.now();
            Film film = new Film();
            film.setFilmText(filmText);
            film.setReleaseYear(now.getYear());
            film.setLanguage(language);
            film.setRentalDuration(rentalDuration);
            film.setRentalRate(rentalRate);
            film.setLength(length);
            film.setReplacementCost(replacementCost);
            film.setRating(rating);
            film.setSpecialFeatures(specialFeatures);
            film.setCategories(categories);
            film.setActors(actors);
            session.persist(film);

            filmText.setFilmId(film.getFilmId());
            session.merge(filmText);

            long storesQuantity = 0;
            List<Store> stores = session.createQuery("from Store", Store.class).getResultList();
            for (Store store : stores) {
                Inventory inventory = new Inventory();
                inventory.setStore(store);
                inventory.setFilm(film);
                session.persist(inventory);
                storesQuantity++;
            }
            transaction.commit();

            Film testFilm = session.get(Film.class, film.getFilmId());
            Assertions.assertNotNull(testFilm);
            Assertions.assertEquals(filmText, testFilm.getFilmText());
            Assertions.assertEquals(filmTitle, testFilm.getTitle());
            Assertions.assertEquals(filmDescription, testFilm.getDescription());
            Assertions.assertEquals(now.getYear(), testFilm.getReleaseYear());
            Assertions.assertEquals(language, testFilm.getLanguage());
            Assertions.assertEquals(rentalDuration, testFilm.getRentalDuration());
            Assertions.assertEquals(rentalRate, testFilm.getRentalRate());
            Assertions.assertEquals(length, testFilm.getLength());
            Assertions.assertEquals(replacementCost, testFilm.getReplacementCost());
            Assertions.assertEquals(rating, testFilm.getRating());
            Assertions.assertEquals(specialFeatures, testFilm.getSpecialFeatures());
            Assertions.assertEquals(categories, testFilm.getCategories());
            Assertions.assertEquals(actors, testFilm.getActors());

            Long testFilmInventoryQuantity = session.createQuery("select count(i) from Inventory i where i.film = :film", Long.class)
                    .setParameter("film", testFilm)
                    .getSingleResult();
            Assertions.assertEquals(storesQuantity, testFilmInventoryQuantity);
        }
    }
}
