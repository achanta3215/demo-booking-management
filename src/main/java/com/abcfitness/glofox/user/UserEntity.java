package com.abcfitness.glofox.user;

import com.abcfitness.glofox.booking.BookingEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    // One user can have many bookings
    @OneToMany(mappedBy = "userEntity")
    @Builder.Default
    @JsonManagedReference
    private List<BookingEntity> bookings = new ArrayList<>();
}

