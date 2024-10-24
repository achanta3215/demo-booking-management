package com.abcfitness.glofox.coaching;

import com.abcfitness.glofox.booking.BookingEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "classEntity")
    @Builder.Default
    @JsonManagedReference
    private List<BookingEntity> bookings = new ArrayList<>();
}

