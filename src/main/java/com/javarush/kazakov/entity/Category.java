package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category", schema = "movie")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id",columnDefinition = "tinyint UNSIGNED", nullable = false)
    private Integer categoryId;
    @Column(nullable = false, length = 25)
    private String name;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_category",
            joinColumns=  @JoinColumn(name="category_id", referencedColumnName="category_id"),
            inverseJoinColumns= @JoinColumn(name="film_id", referencedColumnName="film_id") )
    private List<Film> films = new ArrayList<>();
}
