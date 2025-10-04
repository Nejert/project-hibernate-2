### JRU Hibernate Project 2
#### Tasks:
- Check the database structure.
- Map entities to existing tables.
- Add minimal functionality to verify that the mapping is done correctly.
#### Details:
1. Maven project with dependencies:
    - org.hibernate.orm:hibernate-core
    - com.mysql:mysql-connector-j
    - p6spy:p6spy
2. All necessary entity classes have been created and mapped to tables of the `movie` schema.
3. Added transactional test method that can create a new customer with all dependent fields.
4. Added transactional test method that describes the event "the customer returned a previously rented movie"
(any customer and rental event). The movie rating does not recalculate.
5. Added transactional test method that describes the event
"The customer went to the store and rented inventory. And also made a payment to the staff"
when the movie available for rental. Either there must be no inventory records in the rental table,
or the return_date column of the rental table must be populated for the last rental of this inventory.
6. Added transactional test method that describes the event "a new movie was made and became available for rental".
#### Issues identified:
- Missing **foreign key** in the `film_text` table for the `film_id` field of the `film` table.
- A reference to `inventory` could be created in `store`.
- `inventory` could be a table with the film-store-quantity data;
this would reduce the number of records in this table.
- `customer` could contain a reference to `payment` and `rental`.