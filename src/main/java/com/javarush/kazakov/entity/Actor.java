package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor", schema = "movie",
        indexes = {
        @Index(name = "idx_actor_last_name", columnList = "last_name")
})
@Getter
@Setter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", columnDefinition = "smallint UNSIGNED", nullable = false)
    private Integer actorId;
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_actor",
            joinColumns=  @JoinColumn(name="actor_id", referencedColumnName="actor_id"),
            inverseJoinColumns= @JoinColumn(name="film_id", referencedColumnName="film_id"))
    private List<Film> films = new ArrayList<>();
}
