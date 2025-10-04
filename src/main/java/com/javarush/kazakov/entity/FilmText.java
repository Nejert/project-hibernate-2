package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film_text", schema = "movie",
        indexes = {
                @Index(name = "idx_title_description", columnList = "title, description")
        })
@Getter
@Setter
public class FilmText {
    @Id
    @Column(name = "film_id", nullable = false)
    private Integer filmId;
    @Column(nullable = false)
    private String title;
    private String description;
}
