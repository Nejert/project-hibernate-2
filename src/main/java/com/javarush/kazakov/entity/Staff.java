package com.javarush.kazakov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff", schema = "movie", indexes = {
        @Index(name = "idx_fk_address_id", columnList = "address_id"),
        @Index(name = "idx_fk_store_id", columnList = "store_id")
})
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", columnDefinition = "tinyint UNSIGNED", nullable = false)
    private Integer staffId;
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id",
            foreignKey = @ForeignKey(name = "fk_staff_address"),
            nullable = false)
    private Address address;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] picture;
    @Column(length = 50)
    private String email;
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id",
            foreignKey = @ForeignKey(name = "fk_staff_store"),
            nullable = false)
    private Store store;
    @Column(nullable = false)
    private Boolean active;
    @Column(nullable = false, length = 16)
    private String username;
    @Column(length = 40)
    private String password;
    @Column(name = "last_update", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
