package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental", schema = "movie",
        uniqueConstraints = @UniqueConstraint(name = "rental_date",
                columnNames = {"rental_date", "inventory_id", "customer_id"}),
        indexes = {
        @Index(name = "rental_date", columnList = "rental_date, inventory_id, customer_id", unique = true),
        @Index(name = "idx_fk_customer_id", columnList = "customer_id"),
        @Index(name = "idx_fk_inventory_id", columnList = "inventory_id"),
        @Index(name = "idx_fk_staff_id", columnList = "staff_id")
})
@Getter
@Setter
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;
    @Column(name = "rental_date")
    private LocalDateTime rentalDate;
    @ManyToOne
    @JoinColumn(name = "inventory_id",
            foreignKey = @ForeignKey(name = "fk_rental_inventory"))
    private Inventory inventory;
    @ManyToOne
    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_rental_customer"))
    private Customer customer;
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    @ManyToOne
    @JoinColumn(name = "staff_id",
            foreignKey = @ForeignKey(name = "fk_rental_staff"))
    private Staff staff;
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
