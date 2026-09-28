package com.example.shiftplanner.repository;

import com.shiftplanner.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByDate(LocalDate date);
    List<Shift> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
