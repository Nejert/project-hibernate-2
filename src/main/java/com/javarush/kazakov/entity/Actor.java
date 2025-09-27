package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor", indexes = {
        @Index(name = "idx_actor_last_name", columnList = "last_name")
})
@Getter
@Setter
public class Actor {
    @Id
    @Column(name = "actor_id")
    private Integer actorId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_actor",
            joinColumns=  @JoinColumn(name="actor_id", referencedColumnName="actor_id"),
            inverseJoinColumns= @JoinColumn(name="film_id", referencedColumnName="film_id"))
    private List<Film> films = new ArrayList<>();
}
