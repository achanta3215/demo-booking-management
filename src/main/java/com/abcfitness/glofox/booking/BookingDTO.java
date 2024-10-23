package com.abcfitness.glofox.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long id;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Booking date is required")
    private Date bookingDate;
}

