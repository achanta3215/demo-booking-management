package com.abcfitness.glofox.booking;

import com.abcfitness.glofox.coaching.ClassEntity;
import com.abcfitness.glofox.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "bookings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"class_id", "user_id", "booking_date"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A class may have multiple bookings (Ex: A single class on a given day can be booked by multiple users on the
     * same day till the capacity of the class is filled)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonBackReference
    private ClassEntity classEntity;

    /**
     * A user may have multiple bookings (Ex: Different classes on different days, (or) to same class on multiple days)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity userEntity;

    @Column(name = "booking_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingDate;
}
