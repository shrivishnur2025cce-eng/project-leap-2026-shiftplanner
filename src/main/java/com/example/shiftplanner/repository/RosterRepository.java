package com.example.shiftplanner.repository;

import com.shiftplanner.entity.Roster;
import com.shiftplanner.enums.RosterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RosterRepository extends JpaRepository<Roster, Long> {

    List<Roster> findByEmployeeId(Long employeeId);

    List<Roster> findByEmployeeIdAndStatus(Long employeeId, RosterStatus status);

    @Query("SELECT r FROM Roster r JOIN r.shift s WHERE s.date BETWEEN :startDate AND :endDate")
    List<Roster> findByShiftDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Roster r JOIN r.shift s WHERE r.employee.id = :employeeId AND s.date = :date AND r.status = 'ASSIGNED'")
    List<Roster> findAssignedRostersForEmployeeOnDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Roster r JOIN r.shift s " +
           "WHERE r.employee.id = :employeeId " +
           "AND r.status = 'ASSIGNED' " +
           "AND s.date = :date " +
           "AND s.startTime < :endTime " +
           "AND s.endTime > :startTime " +
           "AND (:excludeRosterId IS NULL OR r.id != :excludeRosterId)")
    boolean existsOverlappingShift(@Param("employeeId") Long employeeId,
                                  @Param("date") LocalDate date,
                                  @Param("startTime") LocalTime startTime,
                                  @Param("endTime") LocalTime endTime,
                                  @Param("excludeRosterId") Long excludeRosterId);

    Optional<Roster> findByEmployeeIdAndShiftIdAndStatus(Long employeeId, Long shiftId, RosterStatus status);
}
