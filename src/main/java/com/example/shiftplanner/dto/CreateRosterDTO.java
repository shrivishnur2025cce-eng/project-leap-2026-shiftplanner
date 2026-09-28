package com.example.shiftplanner.dto;

import jakarta.validation.constraints.NotNull;

public class CreateRosterDTO {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Shift ID is required")
    private Long shiftId;

    public CreateRosterDTO() {}

    public CreateRosterDTO(Long employeeId, Long shiftId) {
        this.employeeId = employeeId;
        this.shiftId = shiftId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long employeeId;
        private Long shiftId;

        public Builder employeeId(Long employeeId) { this.employeeId = employeeId; return this; }
        public Builder shiftId(Long shiftId) { this.shiftId = shiftId; return this; }

        public CreateRosterDTO build() {
            return new CreateRosterDTO(employeeId, shiftId);
        }
    }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getShiftId() { return shiftId; }
    public void setShiftId(Long shiftId) { this.shiftId = shiftId; }
}
