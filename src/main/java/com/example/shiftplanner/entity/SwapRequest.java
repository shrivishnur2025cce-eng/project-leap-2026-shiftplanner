package com.example.shiftplanner.entity;

import com.shiftplanner.enums.SwapRequestStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "swap_requests", indexes = {
    @Index(name = "idx_swap_requester_id", columnList = "requester_id"),
    @Index(name = "idx_swap_colleague_id", columnList = "colleague_id"),
    @Index(name = "idx_swap_roster_id", columnList = "roster_id"),
    @Index(name = "idx_swap_status", columnList = "status")
})
public class SwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id", nullable = false)
    private Employee requester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colleague_id", nullable = false)
    private Employee colleague;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id", nullable = false)
    private Roster roster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requested_shift_id")
    private Shift requestedShift;

    @Column(length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwapRequestStatus status = SwapRequestStatus.PENDING_COLLEAGUE;

    @Column
    private Boolean colleagueApproval;

    @Column
    private Boolean managerApproval;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public SwapRequest() {}

    public SwapRequest(Long id, Employee requester, Employee colleague, Roster roster, Shift requestedShift, String reason, SwapRequestStatus status, Boolean colleagueApproval, Boolean managerApproval, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.requester = requester;
        this.colleague = colleague;
        this.roster = roster;
        this.requestedShift = requestedShift;
        this.reason = reason;
        this.status = status != null ? status : SwapRequestStatus.PENDING_COLLEAGUE;
        this.colleagueApproval = colleagueApproval;
        this.managerApproval = managerApproval;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        this.updatedAt = now;
        if (this.status == null) {
            this.status = SwapRequestStatus.PENDING_COLLEAGUE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Employee requester;
        private Employee colleague;
        private Roster roster;
        private Shift requestedShift;
        private String reason;
        private SwapRequestStatus status = SwapRequestStatus.PENDING_COLLEAGUE;
        private Boolean colleagueApproval;
        private Boolean managerApproval;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder requester(Employee requester) { this.requester = requester; return this; }
        public Builder colleague(Employee colleague) { this.colleague = colleague; return this; }
        public Builder roster(Roster roster) { this.roster = roster; return this; }
        public Builder requestedShift(Shift requestedShift) { this.requestedShift = requestedShift; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder status(SwapRequestStatus status) { this.status = status; return this; }
        public Builder colleagueApproval(Boolean colleagueApproval) { this.colleagueApproval = colleagueApproval; return this; }
        public Builder managerApproval(Boolean managerApproval) { this.managerApproval = managerApproval; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public SwapRequest build() {
            return new SwapRequest(id, requester, colleague, roster, requestedShift, reason, status, colleagueApproval, managerApproval, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getRequester() { return requester; }
    public void setRequester(Employee requester) { this.requester = requester; }

    public Employee getColleague() { return colleague; }
    public void setColleague(Employee colleague) { this.colleague = colleague; }

    public Roster getRoster() { return roster; }
    public void setRoster(Roster roster) { this.roster = roster; }

    public Shift getRequestedShift() { return requestedShift; }
    public void setRequestedShift(Shift requestedShift) { this.requestedShift = requestedShift; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public SwapRequestStatus getStatus() { return status; }
    public void setStatus(SwapRequestStatus status) { this.status = status; }

    public Boolean getColleagueApproval() { return colleagueApproval; }
    public void setColleagueApproval(Boolean colleagueApproval) { this.colleagueApproval = colleagueApproval; }

    public Boolean getManagerApproval() { return managerApproval; }
    public void setManagerApproval(Boolean managerApproval) { this.managerApproval = managerApproval; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
