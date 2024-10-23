package com.abcfitness.glofox;

import com.abcfitness.glofox.booking.BookingController;
import com.abcfitness.glofox.booking.BookingDTO;
import com.abcfitness.glofox.booking.BookingEntity;
import com.abcfitness.glofox.booking.BookingService;
import com.abcfitness.glofox.exceptions.ClassCapacityExceededException;
import com.abcfitness.glofox.exceptions.DuplicateBookingException;
import com.abcfitness.glofox.exceptions.InvalidBookingDateException;
import com.abcfitness.glofox.user.UserEntity;
import com.abcfitness.glofox.coaching.ClassEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookingDTO validBookingDTO;

    @BeforeEach
    public void setup() {
        validBookingDTO = new BookingDTO(
                null,
                1L,
                1L,
                new Date()
        );
    }

    @Test
    public void testCreateBooking_Success() throws Exception {

        BookingEntity bookingEntity = new BookingEntity(1L, new ClassEntity(), new UserEntity(), new Date());
        when(bookingService.saveOrUpdateBooking(any(BookingDTO.class))).thenReturn(bookingEntity);

        // Perform POST request to create booking and expect 200 OK
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateBooking_DuplicateBooking() throws Exception {
        // Simulate a duplicate booking scenario by throwing a custom DuplicateBookingException
        doThrow(new DuplicateBookingException("Booking already exists")).when(bookingService)
                .saveOrUpdateBooking(any(BookingDTO.class));

        // Perform POST request to create booking and expect 409 Conflict
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Booking already exists"));
    }

    @Test
    public void testCreateBooking_ClassCapacityExceeded() throws Exception {
        // Simulate class capacity exceeded by throwing ClassCapacityExceededException
        doThrow(new ClassCapacityExceededException("Class capacity exceeded"))
                .when(bookingService).saveOrUpdateBooking(any(BookingDTO.class));

        // Perform POST request to create booking and expect 409 Conflict
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Class capacity exceeded"));
    }

    @Test
    public void testCreateBooking_InvalidBookingDate() throws Exception {
        // Simulate invalid booking date scenario by throwing InvalidBookingDateException
        doThrow(new InvalidBookingDateException("Booking date must be within the class date range."))
                .when(bookingService).saveOrUpdateBooking(any(BookingDTO.class));

        // Perform POST request and expect 400 Bad Request
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Booking date must be within the class date range."));
    }

    // handle entity not found exception
    @Test
    public void testCreateBooking_InvalidRequest() throws Exception {
        // Create an invalid BookingDTO (e.g., missing classId)
        BookingDTO invalidBookingDTO = new BookingDTO(null, null, 1L, new Date());

        // Perform POST request with invalid data and expect 400 Bad Request
        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookingDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCreateBooking_InvalidClassId() throws Exception {
        // Create a valid BookingDTO with a non-existent classId
        Long invalidClassId = 999L;
        BookingDTO bookingDTO = new BookingDTO(null, invalidClassId, 1L, new Date());

        doThrow(new EntityNotFoundException("ClassID not found"))
                .when(bookingService).saveOrUpdateBooking(any(BookingDTO.class));

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ClassID not found"));
    }

}

