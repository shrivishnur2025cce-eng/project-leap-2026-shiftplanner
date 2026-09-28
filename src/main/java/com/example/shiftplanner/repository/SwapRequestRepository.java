package com.example.shiftplanner.repository;

import com.shiftplanner.entity.SwapRequest;
import com.shiftplanner.enums.SwapRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {

    @Query("SELECT s FROM SwapRequest s WHERE s.requester.id = :employeeId OR s.colleague.id = :employeeId ORDER BY s.createdAt DESC")
    List<SwapRequest> findByEmployeeId(@Param("employeeId") Long employeeId);

    List<SwapRequest> findByStatus(SwapRequestStatus status);

    @Query("SELECT s FROM SwapRequest s WHERE s.status IN ('PENDING_COLLEAGUE', 'COLLEAGUE_APPROVED') ORDER BY s.createdAt DESC")
    List<SwapRequest> findAllPendingRequests();

    @Query("SELECT s FROM SwapRequest s WHERE s.roster.id = :rosterId AND s.status IN ('PENDING_COLLEAGUE', 'COLLEAGUE_APPROVED')")
    List<SwapRequest> findActivePendingRequestsForRoster(@Param("rosterId") Long rosterId);
}
