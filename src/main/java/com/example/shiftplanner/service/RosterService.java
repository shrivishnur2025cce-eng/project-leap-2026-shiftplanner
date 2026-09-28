package com.example.shiftplanner.service;

import com.shiftplanner.dto.CreateRosterDTO;
import com.shiftplanner.dto.RosterDTO;
import com.shiftplanner.dto.WeeklyRosterResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface RosterService {
    List<RosterDTO> getAllRosters();
    WeeklyRosterResponseDTO getWeeklyRoster(LocalDate startDate);
    List<RosterDTO> getRostersByEmployeeId(Long employeeId);
    RosterDTO getRosterById(Long id);
    RosterDTO createRoster(CreateRosterDTO createRosterDTO);
    RosterDTO updateRoster(Long id, CreateRosterDTO createRosterDTO);
    void deleteRoster(Long id);
}
