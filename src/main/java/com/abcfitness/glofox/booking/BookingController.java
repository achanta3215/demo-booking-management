package com.abcfitness.glofox.booking;

import com.abcfitness.glofox.exceptions.ClassCapacityExceededException;
import com.abcfitness.glofox.exceptions.DuplicateBookingException;
import com.abcfitness.glofox.exceptions.InvalidBookingDateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        try {
            BookingEntity bookingEntity = bookingService.saveOrUpdateBooking(bookingDTO);
            return ResponseEntity.ok(bookingEntity);
        } catch (DuplicateBookingException | ClassCapacityExceededException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        } catch (InvalidBookingDateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

}
