package com.example.shiftplanner.dto;

import com.shiftplanner.enums.RosterStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RosterDTO {
    private Long id;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;

    @NotNull(message = "Shift ID is required")
    private Long shiftId;
    private String shiftName;
    private String date;
    private String startTime;
    private String endTime;

    private RosterStatus status;
    private LocalDateTime createdAt;

    public RosterDTO() {}

    public RosterDTO(Long id, Long employeeId, String employeeName, String employeeEmail, Long shiftId, String shiftName, String date, String startTime, String endTime, RosterStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.shiftId = shiftId;
        this.shiftName = shiftName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long employeeId;
        private String employeeName;
        private String employeeEmail;
        private Long shiftId;
        private String shiftName;
        private String date;
        private String startTime;
        private String endTime;
        private RosterStatus status;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder employeeId(Long employeeId) { this.employeeId = employeeId; return this; }
        public Builder employeeName(String employeeName) { this.employeeName = employeeName; return this; }
        public Builder employeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; return this; }
        public Builder shiftId(Long shiftId) { this.shiftId = shiftId; return this; }
        public Builder shiftName(String shiftName) { this.shiftName = shiftName; return this; }
        public Builder date(String date) { this.date = date; return this; }
        public Builder startTime(String startTime) { this.startTime = startTime; return this; }
        public Builder endTime(String endTime) { this.endTime = endTime; return this; }
        public Builder status(RosterStatus status) { this.status = status; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public RosterDTO build() {
            return new RosterDTO(id, employeeId, employeeName, employeeEmail, shiftId, shiftName, date, startTime, endTime, status, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

    public Long getShiftId() { return shiftId; }
    public void setShiftId(Long shiftId) { this.shiftId = shiftId; }

    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public RosterStatus getStatus() { return status; }
    public void setStatus(RosterStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
