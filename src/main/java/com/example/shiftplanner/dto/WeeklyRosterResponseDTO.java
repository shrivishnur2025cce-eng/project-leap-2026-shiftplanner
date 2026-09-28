package com.example.shiftplanner.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class WeeklyRosterResponseDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> dates;
    private List<EmployeeRosterRowDTO> rows;

    public WeeklyRosterResponseDTO() {}

    public WeeklyRosterResponseDTO(LocalDate startDate, LocalDate endDate, List<LocalDate> dates, List<EmployeeRosterRowDTO> rows) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dates = dates;
        this.rows = rows;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDate startDate;
        private LocalDate endDate;
        private List<LocalDate> dates;
        private List<EmployeeRosterRowDTO> rows;

        public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public Builder dates(List<LocalDate> dates) { this.dates = dates; return this; }
        public Builder rows(List<EmployeeRosterRowDTO> rows) { this.rows = rows; return this; }

        public WeeklyRosterResponseDTO build() {
            return new WeeklyRosterResponseDTO(startDate, endDate, dates, rows);
        }
    }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public List<LocalDate> getDates() { return dates; }
    public void setDates(List<LocalDate> dates) { this.dates = dates; }

    public List<EmployeeRosterRowDTO> getRows() { return rows; }
    public void setRows(List<EmployeeRosterRowDTO> rows) { this.rows = rows; }

    public static class EmployeeRosterRowDTO {
        private EmployeeDTO employee;
        private Map<String, List<RosterDTO>> dailyShifts;

        public EmployeeRosterRowDTO() {}

        public EmployeeRosterRowDTO(EmployeeDTO employee, Map<String, List<RosterDTO>> dailyShifts) {
            this.employee = employee;
            this.dailyShifts = dailyShifts;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private EmployeeDTO employee;
            private Map<String, List<RosterDTO>> dailyShifts;

            public Builder employee(EmployeeDTO employee) { this.employee = employee; return this; }
            public Builder dailyShifts(Map<String, List<RosterDTO>> dailyShifts) { this.dailyShifts = dailyShifts; return this; }

            public EmployeeRosterRowDTO build() {
                return new EmployeeRosterRowDTO(employee, dailyShifts);
            }
        }

        public EmployeeDTO getEmployee() { return employee; }
        public void setEmployee(EmployeeDTO employee) { this.employee = employee; }

        public Map<String, List<RosterDTO>> getDailyShifts() { return dailyShifts; }
        public void setDailyShifts(Map<String, List<RosterDTO>> dailyShifts) { this.dailyShifts = dailyShifts; }
    }
}
