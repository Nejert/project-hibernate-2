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
    @Column(name = "address_id")
    private Integer address_id;
    private String address;
    private String address2;
    private String district;
    @ManyToOne
    @JoinColumn(name = "city_id",
            foreignKey = @ForeignKey(name = "fk_address_city"))
    private City city;
    @Column(name = "postal_code")
    private String postal_code;
    private String phone;
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime last_update;
}
