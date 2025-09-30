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
    @Column(name = "city_id")
    private Integer cityId;
    private String city;
    @ManyToOne
    @JoinColumn(name = "country_id",
            foreignKey = @ForeignKey(name = "fk_city_country"))
    private Country country;
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
