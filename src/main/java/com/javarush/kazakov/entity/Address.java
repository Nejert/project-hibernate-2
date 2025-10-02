package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "address", schema = "movie", indexes = {
        @Index(name = "idx_fk_city_id", columnList = "city_id")
})
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", columnDefinition = "smallint UNSIGNED", nullable = false)
    private Integer address_id;
    @Column(nullable = false, length = 50)
    private String address;
    @Column(length = 50)
    private String address2;
    @Column(nullable = false, length = 20)
    private String district;
    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id",
            foreignKey = @ForeignKey(name = "fk_address_city"),
            nullable = false)
    private City city;
    @Column(name = "postal_code", length = 10)
    private String postal_code;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime last_update;
}
