package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "city", schema = "movie", indexes = {
        @Index(name = "idx_fk_country_id", columnList = "country_id")
})
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", columnDefinition = "smallint UNSIGNED", nullable = false)
    private Integer cityId;
    @Column(nullable = false, length = 50)
    private String city;
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id",
            foreignKey = @ForeignKey(name = "fk_city_country"),
            nullable = false)
    private Country country;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
