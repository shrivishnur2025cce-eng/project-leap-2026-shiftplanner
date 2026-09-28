package com.example.shiftplanner.mapper;

import com.example.shiftplanner.dto.EmployeeDTO;
import com.example.shiftplanner.dto.*;
import com.example.shiftplanner.entity.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DtoMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public EmployeeDTO toEmployeeDTO(Employee employee) {
        if (employee == null) return null;
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .active(employee.isActive())
                .build();
    }

    public Employee toEmployeeEntity(EmployeeDTO dto) {
        if (dto == null) return null;
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .active(dto.isActive())
                .build();
    }

    public ShiftDTO toShiftDTO(Shift shift) {
        if (shift == null) return null;
        return ShiftDTO.builder()
                .id(shift.getId())
                .shiftName(shift.getShiftName())
                .date(shift.getDate())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .build();
    }

    public Shift toShiftEntity(ShiftDTO dto) {
        if (dto == null) return null;
        return Shift.builder()
                .id(dto.getId())
                .shiftName(dto.getShiftName())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    public RosterDTO toRosterDTO(Roster roster) {
        if (roster == null) return null;
        return RosterDTO.builder()
                .id(roster.getId())
                .employeeId(roster.getEmployee() != null ? roster.getEmployee().getId() : null)
                .employeeName(roster.getEmployee() != null ? roster.getEmployee().getName() : null)
                .employeeEmail(roster.getEmployee() != null ? roster.getEmployee().getEmail() : null)
                .shiftId(roster.getShift() != null ? roster.getShift().getId() : null)
                .shiftName(roster.getShift() != null ? roster.getShift().getShiftName() : null)
                .date(roster.getShift() != null && roster.getShift().getDate() != null ? roster.getShift().getDate().format(DATE_FORMATTER) : null)
                .startTime(roster.getShift() != null && roster.getShift().getStartTime() != null ? roster.getShift().getStartTime().format(TIME_FORMATTER) : null)
                .endTime(roster.getShift() != null && roster.getShift().getEndTime() != null ? roster.getShift().getEndTime().format(TIME_FORMATTER) : null)
                .status(roster.getStatus())
                .createdAt(roster.getCreatedAt())
                .build();
    }

    public SwapRequestDTO toSwapRequestDTO(SwapRequest request) {
        if (request == null) return null;

        Roster roster = request.getRoster();
        Shift shift = roster != null ? roster.getShift() : null;
        Shift requestedShift = request.getRequestedShift();

        return SwapRequestDTO.builder()
                .id(request.getId())
                .requesterId(request.getRequester() != null ? request.getRequester().getId() : null)
                .requesterName(request.getRequester() != null ? request.getRequester().getName() : null)
                .requesterEmail(request.getRequester() != null ? request.getRequester().getEmail() : null)
                .colleagueId(request.getColleague() != null ? request.getColleague().getId() : null)
                .colleagueName(request.getColleague() != null ? request.getColleague().getName() : null)
                .colleagueEmail(request.getColleague() != null ? request.getColleague().getEmail() : null)
                .rosterId(roster != null ? roster.getId() : null)
                .shiftName(shift != null ? shift.getShiftName() : null)
                .shiftDate(shift != null && shift.getDate() != null ? shift.getDate().format(DATE_FORMATTER) : null)
                .shiftStartTime(shift != null && shift.getStartTime() != null ? shift.getStartTime().format(TIME_FORMATTER) : null)
                .shiftEndTime(shift != null && shift.getEndTime() != null ? shift.getEndTime().format(TIME_FORMATTER) : null)
                .requestedShiftId(requestedShift != null ? requestedShift.getId() : null)
                .requestedShiftName(requestedShift != null ? requestedShift.getShiftName() : null)
                .reason(request.getReason())
                .status(request.getStatus())
                .colleagueApproval(request.getColleagueApproval())
                .managerApproval(request.getManagerApproval())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
}
