package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", schema = "movie", indexes = {
        @Index(name = "idx_fk_film_id", columnList = "film_id"),
        @Index(name = "idx_store_id_film_id", columnList = "store_id,film_id")
})
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private Integer inventoryId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id",
            foreignKey = @ForeignKey(name = "fk_inventory_film"),
            nullable = false)
    private Film film;
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id",
            foreignKey = @ForeignKey(name = "fk_inventory_store"),
            nullable = false)
    private Store store;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
