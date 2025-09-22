package com.javarush.kazakov.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "film")
@Getter
@Setter
@ToString
public class Film {
    @Id
    Integer film_id;
    String title;
    String description;
    Integer release_year;
    Integer language_id;
    Integer original_language_id;
    Integer rental_duration;
    Double rental_rate;
    Integer length;
    Double replacement_cost;
    String rating;
    String special_features;
    LocalDateTime last_update;
}
