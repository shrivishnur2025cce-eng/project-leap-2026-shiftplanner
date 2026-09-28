package com.example.shiftplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShiftDTO {
    private Long id;

    @NotBlank(message = "Shift name is required")
    private String shiftName;

    @NotNull(message = "Shift date is required")
    private LocalDate date;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    public ShiftDTO() {}

    public ShiftDTO(Long id, String shiftName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.shiftName = shiftName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String shiftName;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder shiftName(String shiftName) { this.shiftName = shiftName; return this; }
        public Builder date(LocalDate date) { this.date = date; return this; }
        public Builder startTime(LocalTime startTime) { this.startTime = startTime; return this; }
        public Builder endTime(LocalTime endTime) { this.endTime = endTime; return this; }

        public ShiftDTO build() {
            return new ShiftDTO(id, shiftName, date, startTime, endTime);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}
