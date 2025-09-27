package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.RatingConverter;
import com.javarush.kazakov.entity.misc.Rating;
import com.javarush.kazakov.entity.misc.SpecialFeature;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "film", indexes = {
        @Index(name = "film_text", columnList = "title, description")
})
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
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
    @ManyToOne
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_category",
            joinColumns=  @JoinColumn(name="film_id", referencedColumnName="film_id"),
            inverseJoinColumns= @JoinColumn(name="category_id", referencedColumnName="category_id"))
    private List<Category> categories = new ArrayList<>();

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
