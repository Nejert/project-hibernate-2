package com.javarush.kazakov.entity;

import com.javarush.kazakov.config.RatingConverter;
import com.javarush.kazakov.entity.misc.Rating;
import com.javarush.kazakov.entity.misc.SpecialFeature;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "film", schema = "movie", indexes = {
        @Index(name = "idx_title", columnList = "title"),
        @Index(name = "idx_fk_language_id", columnList = "language_id"),
        @Index(name = "idx_fk_original_language_id", columnList = "original_language_id")
})
@Getter
@Setter
@ToString(exclude = "specialFeaturesString")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", columnDefinition = "smallint UNSIGNED", nullable = false)
    private Integer filmId;
    @OneToOne(optional = false)
    @JoinColumn(name = "film_id")
    private FilmText filmText;
    private String title;
    private String description;
    @Column(name = "release_year")
    private Integer releaseYear;
    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id",
            foreignKey = @ForeignKey(name = "fk_film_language"),
            nullable = false)
    private Language language;
    @ManyToOne
    @JoinColumn(name = "original_language_id",
            foreignKey = @ForeignKey(name = "fk_film_language_original"))
    private Language originalLanguage;
    @Column(name = "rental_duration", columnDefinition = "tinyint UNSIGNED",nullable = false)
    private Integer rentalDuration;
    @Column(name = "rental_rate",nullable = false)
    private Double rentalRate;
    @Column(columnDefinition = "smallint UNSIGNED")
    private Integer length;
    @Column(name = "replacement_cost", nullable = false)
    private Double replacementCost;
    @Convert(converter = RatingConverter.class)
    private Rating rating;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "special_features")
    private String specialFeaturesString;
    @Transient
    private Set<SpecialFeature> specialFeatures;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_category",
            joinColumns=  @JoinColumn(name="film_id", referencedColumnName="film_id", foreignKey = @ForeignKey(name = "fk_film_category_film")),
            inverseJoinColumns= @JoinColumn(name="category_id", referencedColumnName="category_id",foreignKey = @ForeignKey(name = "fk_film_category_category")))
    private List<Category> categories = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_actor",
            joinColumns=  @JoinColumn(name="film_id", referencedColumnName="film_id", foreignKey = @ForeignKey(name = "fk_film_actor_film")),
            inverseJoinColumns= @JoinColumn(name="actor_id", referencedColumnName="actor_id"), foreignKey = @ForeignKey(name = "fk_film_actor_actor"))
    private List<Actor> actors = new ArrayList<>();

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

    public void setFilmText(FilmText filmText) {
        this.filmText = filmText;
        this.title = filmText.getTitle();
        this.description = filmText.getDescription();
    }
}
