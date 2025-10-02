package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer", schema = "movie", indexes = {
        @Index(name = "idx_fk_address_id", columnList = "address_id"),
        @Index(name = "idx_fk_store_id", columnList = "store_id"),
        @Index(name = "idx_last_name", columnList = "last_name")
})
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "smallint UNSIGNED", nullable = false)
    private Integer customerId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id",
            foreignKey = @ForeignKey(name = "fk_customer_store"),
            nullable = false)
    private Store store;
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @Column(length = 50)
    private String email;
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id",
            foreignKey = @ForeignKey(name = "fk_customer_address"),
            nullable = false)
    private Address address;
    @Column(nullable = false)
    private Boolean active;
    @Column(name = "create_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime createDate;
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
