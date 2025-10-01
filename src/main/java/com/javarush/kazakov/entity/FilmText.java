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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Integer film_id;
    private String title;
    private String description;
}
