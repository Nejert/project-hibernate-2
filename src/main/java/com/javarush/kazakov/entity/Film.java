package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.RatingConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "film")
@Getter
@Setter
@ToString(exclude = "specialFeaturesString")
public class Film {
    @Id
    @Column(name = "film_id")
    private Integer filmId;
    private String title;
    private String description;
    @Column(name = "release_year")
    private Integer releaseYear;
    @OneToOne
    @JoinColumn(name = "language_id")
    private Language language;
    @OneToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;
    @Column(name = "rental_duration")
    private Integer rentalDuration;
    @Column(name = "rental_rate")
    private Double rentalRate;
    private Integer length;
    @Column(name = "replacement_cost")
    private Double replacementCost;
    @Convert(converter = RatingConverter.class)
    private Rating rating;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "special_features")
    private String specialFeaturesString;
    @Transient
    private Set<SpecialFeature> specialFeatures;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    public Set<SpecialFeature> getSpecialFeatures() {
        if (specialFeaturesString == null) {
            return Collections.emptySet();
        } else {
            return Arrays.stream(specialFeaturesString.split(","))
                    .map(e -> SpecialFeature.valueOf(e.toUpperCase().replace(' ', '_')))
                    .collect(Collectors.toUnmodifiableSet());
        }
    }

    public void setSpecialFeatures(Set<SpecialFeature> specialFeatures) {
        this.specialFeatures = specialFeatures;
        if (specialFeatures == null) {
            specialFeaturesString = null;
        } else {
            specialFeaturesString = specialFeatures.stream()
                    .map(SpecialFeature::getFeature)
                    .collect(Collectors.joining(","));
        }
    }
}
