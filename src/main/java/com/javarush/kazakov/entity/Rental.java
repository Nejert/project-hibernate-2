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
    @Column(name = "rental_id", nullable = false)
    private Integer rentalId;
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_id",
            foreignKey = @ForeignKey(name = "fk_rental_inventory"),
            nullable = false)
    private Inventory inventory;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_rental_customer"),
            nullable = false)
    private Customer customer;
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id",
            foreignKey = @ForeignKey(name = "fk_rental_staff"),
            nullable = false)
    private Staff staff;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
