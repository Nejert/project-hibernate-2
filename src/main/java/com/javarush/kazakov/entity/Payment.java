package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment", schema = "movie",
        indexes = {
                @Index(name = "fk_payment_rental", columnList = "rental_id"),
                @Index(name = "idx_fk_customer_id", columnList = "customer_id"),
                @Index(name = "idx_fk_staff_id", columnList = "staff_id")
        })
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;
    @ManyToOne
    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_payment_customer"))
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "staff_id",
            foreignKey = @ForeignKey(name = "fk_payment_staff"))
    private Staff staff;
    @ManyToOne
    @JoinColumn(name = "rental_id",
            foreignKey = @ForeignKey(name = "fk_payment_rental"))
    private Rental rental;
    private Double amount;
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
