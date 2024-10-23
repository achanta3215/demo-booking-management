package com.abcfitness.glofox.booking;

import com.abcfitness.glofox.coaching.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    long countByClassEntity(ClassEntity classEntity);
}
