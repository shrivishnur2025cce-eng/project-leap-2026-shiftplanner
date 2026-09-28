package com.example.shiftplanner.service.impl;

import com.shiftplanner.dto.CreateRosterDTO;
import com.shiftplanner.dto.EmployeeDTO;
import com.shiftplanner.dto.RosterDTO;
import com.shiftplanner.dto.WeeklyRosterResponseDTO;
import com.shiftplanner.entity.Employee;
import com.shiftplanner.entity.Roster;
import com.shiftplanner.entity.Shift;
import com.shiftplanner.enums.RosterStatus;
import com.shiftplanner.exception.EmployeeNotFoundException;
import com.shiftplanner.exception.RosterNotFoundException;
import com.shiftplanner.exception.ShiftNotFoundException;
import com.shiftplanner.exception.ShiftOverlapException;
import com.shiftplanner.mapper.DtoMapper;
import com.shiftplanner.repository.EmployeeRepository;
import com.shiftplanner.repository.RosterRepository;
import com.shiftplanner.repository.ShiftRepository;
import com.shiftplanner.service.RosterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RosterServiceImpl implements RosterService {

    private final RosterRepository rosterRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final DtoMapper dtoMapper;

    public RosterServiceImpl(RosterRepository rosterRepository, EmployeeRepository employeeRepository, ShiftRepository shiftRepository, DtoMapper dtoMapper) {
        this.rosterRepository = rosterRepository;
        this.employeeRepository = employeeRepository;
        this.shiftRepository = shiftRepository;
        this.dtoMapper = dtoMapper;
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @Transactional(readOnly = true)
    public List<RosterDTO> getAllRosters() {
        return rosterRepository.findAll().stream()
                .map(dtoMapper::toRosterDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WeeklyRosterResponseDTO getWeeklyRoster(LocalDate startDate) {
        if (startDate == null) {
            startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        } else {
            startDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        LocalDate endDate = startDate.plusDays(6);

        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(startDate.plusDays(i));
        }

        List<Roster> weeklyRosters = rosterRepository.findByShiftDateBetween(startDate, endDate);
        List<Employee> allEmployees = employeeRepository.findAll();

        Map<Long, Map<String, List<RosterDTO>>> employeeMap = new LinkedHashMap<>();

        for (Employee emp : allEmployees) {
            Map<String, List<RosterDTO>> dailyMap = new LinkedHashMap<>();
            for (LocalDate d : dates) {
                dailyMap.put(d.format(DATE_FORMATTER), new ArrayList<>());
            }
            employeeMap.put(emp.getId(), dailyMap);
        }

        for (Roster r : weeklyRosters) {
            if (r.getStatus() == RosterStatus.ASSIGNED && r.getEmployee() != null && r.getShift() != null) {
                Long empId = r.getEmployee().getId();
                String dateStr = r.getShift().getDate().format(DATE_FORMATTER);
                if (employeeMap.containsKey(empId) && employeeMap.get(empId).containsKey(dateStr)) {
                    employeeMap.get(empId).get(dateStr).add(dtoMapper.toRosterDTO(r));
                }
            }
        }

        List<WeeklyRosterResponseDTO.EmployeeRosterRowDTO> rows = new ArrayList<>();
        for (Employee emp : allEmployees) {
            rows.add(WeeklyRosterResponseDTO.EmployeeRosterRowDTO.builder()
                    .employee(dtoMapper.toEmployeeDTO(emp))
                    .dailyShifts(employeeMap.get(emp.getId()))
                    .build());
        }

        return WeeklyRosterResponseDTO.builder()
                .startDate(startDate)
                .endDate(endDate)
                .dates(dates)
                .rows(rows)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RosterDTO> getRostersByEmployeeId(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
        return rosterRepository.findByEmployeeId(employeeId).stream()
                .filter(r -> r.getStatus() == RosterStatus.ASSIGNED)
                .map(dtoMapper::toRosterDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RosterDTO getRosterById(Long id) {
        Roster roster = rosterRepository.findById(id)
                .orElseThrow(() -> new RosterNotFoundException("Roster assignment not found with id: " + id));
        return dtoMapper.toRosterDTO(roster);
    }

    @Override
    public RosterDTO createRoster(CreateRosterDTO createRosterDTO) {
        Employee employee = employeeRepository.findById(createRosterDTO.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + createRosterDTO.getEmployeeId()));

        if (!employee.isActive()) {
            throw new IllegalArgumentException("Cannot assign shift to inactive employee: " + employee.getName());
        }

        Shift shift = shiftRepository.findById(createRosterDTO.getShiftId())
                .orElseThrow(() -> new ShiftNotFoundException("Shift not found with id: " + createRosterDTO.getShiftId()));

        boolean hasOverlap = rosterRepository.existsOverlappingShift(
                employee.getId(),
                shift.getDate(),
                shift.getStartTime(),
                shift.getEndTime(),
                null
        );

        if (hasOverlap) {
            throw new ShiftOverlapException("Employee already has an overlapping shift on " + shift.getDate());
        }

        Roster roster = Roster.builder()
                .employee(employee)
                .shift(shift)
                .status(RosterStatus.ASSIGNED)
                .build();

        Roster saved = rosterRepository.save(roster);
        return dtoMapper.toRosterDTO(saved);
    }

    @Override
    public RosterDTO updateRoster(Long id, CreateRosterDTO createRosterDTO) {
        Roster existing = rosterRepository.findById(id)
                .orElseThrow(() -> new RosterNotFoundException("Roster assignment not found with id: " + id));

        Employee employee = employeeRepository.findById(createRosterDTO.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + createRosterDTO.getEmployeeId()));

        if (!employee.isActive()) {
            throw new IllegalArgumentException("Cannot assign shift to inactive employee: " + employee.getName());
        }

        Shift shift = shiftRepository.findById(createRosterDTO.getShiftId())
                .orElseThrow(() -> new ShiftNotFoundException("Shift not found with id: " + createRosterDTO.getShiftId()));

        boolean hasOverlap = rosterRepository.existsOverlappingShift(
                employee.getId(),
                shift.getDate(),
                shift.getStartTime(),
                shift.getEndTime(),
                id
        );

        if (hasOverlap) {
            throw new ShiftOverlapException("Employee already has an overlapping shift on " + shift.getDate());
        }

        existing.setEmployee(employee);
        existing.setShift(shift);
        Roster updated = rosterRepository.save(existing);
        return dtoMapper.toRosterDTO(updated);
    }

    @Override
    public void deleteRoster(Long id) {
        Roster existing = rosterRepository.findById(id)
                .orElseThrow(() -> new RosterNotFoundException("Roster assignment not found with id: " + id));
        existing.setStatus(RosterStatus.CANCELLED);
        rosterRepository.save(existing);
    }
}
