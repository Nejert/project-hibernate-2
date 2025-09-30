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
    @Column(name = "staff_id")
    private Integer staffId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "address_id",
            foreignKey = @ForeignKey(name = "fk_staff_address"))
    private Address address;
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] picture;
    private String email;
    @ManyToOne
    @JoinColumn(name = "store_id",
            foreignKey = @ForeignKey(name = "fk_staff_store"))
    private Store store;
    private Boolean active;
    private String username;
    private String password;
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
