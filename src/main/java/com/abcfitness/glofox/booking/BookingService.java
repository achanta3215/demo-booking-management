package com.abcfitness.glofox.booking;

import com.abcfitness.glofox.coaching.ClassEntity;
import com.abcfitness.glofox.coaching.ClassService;
import com.abcfitness.glofox.exceptions.ClassCapacityExceededException;
import com.abcfitness.glofox.exceptions.InvalidBookingDateException;
import com.abcfitness.glofox.user.UserEntity;
import com.abcfitness.glofox.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ClassService classService;

    @Autowired
    UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    public BookingEntity saveOrUpdateBooking(BookingDTO bookingDTO) {
        ClassEntity classEntity = classService.getClassById(bookingDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("ClassID not found"));
        Date bookingDate = bookingDTO.getBookingDate();
        if (bookingDate.before(classEntity.getStartDate()) || bookingDate.after(classEntity.getEndDate())) {
            throw new InvalidBookingDateException("Booking date must be within the class date range.");
        }
        long currentBookingCount = bookingRepository.countByClassEntity(classEntity);

        // Multi-threading needs to be handled using a specific architecture to deal with the same
        // Ex: 1 Database trigger
        // Ex: 2 MQ implementation to handle all bookings related to a single class in a sequential order
        if (currentBookingCount >= classEntity.getCapacity()) {
            throw new ClassCapacityExceededException("Class capacity exceeded");
        }

        UserEntity userEntity = userService.getUserById(bookingDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("UserId not found"));

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setClassEntity(classEntity);
        bookingEntity.setUserEntity(userEntity);
        bookingEntity.setBookingDate(bookingDTO.getBookingDate());

        return bookingRepository.save(bookingEntity);
    }
}
