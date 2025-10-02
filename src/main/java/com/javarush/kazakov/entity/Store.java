package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "store", schema = "movie", indexes = {
        @Index(name = "idx_unique_manager", columnList = "manager_staff_id", unique = true),
        @Index(name = "idx_fk_address_id", columnList = "address_id")
})
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", columnDefinition = "tinyint UNSIGNED", nullable = false)
    private Integer storeId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "manager_staff_id",
            foreignKey = @ForeignKey(name = "fk_store_staff"),
            nullable = false)
    private Staff managerStaff;
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id",
            foreignKey = @ForeignKey(name = "fk_store_address"),
            nullable = false)
    private Address address;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
