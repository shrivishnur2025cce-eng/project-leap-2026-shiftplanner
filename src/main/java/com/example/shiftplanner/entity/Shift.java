package com.example.shiftplanner.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "shifts", indexes = {
    @Index(name = "idx_shift_date", columnList = "date"),
    @Index(name = "idx_shift_name", columnList = "shiftName")
})
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shiftName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public Shift() {}

    public Shift(Long id, String shiftName, LocalDate date, LocalTime startTime, LocalTime endTime) {
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

        public Shift build() {
            return new Shift(id, shiftName, date, startTime, endTime);
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
