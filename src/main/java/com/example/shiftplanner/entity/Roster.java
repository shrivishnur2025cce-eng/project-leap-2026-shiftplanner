package com.example.shiftplanner.entity;

import com.shiftplanner.enums.RosterStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rosters", indexes = {
    @Index(name = "idx_roster_employee_id", columnList = "employee_id"),
    @Index(name = "idx_roster_shift_id", columnList = "shift_id"),
    @Index(name = "idx_roster_status", columnList = "status")
})
public class Roster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RosterStatus status = RosterStatus.ASSIGNED;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Roster() {}

    public Roster(Long id, Employee employee, Shift shift, RosterStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.employee = employee;
        this.shift = shift;
        this.status = status != null ? status : RosterStatus.ASSIGNED;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = RosterStatus.ASSIGNED;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Employee employee;
        private Shift shift;
        private RosterStatus status = RosterStatus.ASSIGNED;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder employee(Employee employee) { this.employee = employee; return this; }
        public Builder shift(Shift shift) { this.shift = shift; return this; }
        public Builder status(RosterStatus status) { this.status = status; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Roster build() {
            return new Roster(id, employee, shift, status, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }

    public RosterStatus getStatus() { return status; }
    public void setStatus(RosterStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
