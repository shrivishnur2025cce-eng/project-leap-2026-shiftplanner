package com.example.shiftplanner.controller;

import com.shiftplanner.dto.CreateRosterDTO;
import com.shiftplanner.dto.RosterDTO;
import com.shiftplanner.dto.WeeklyRosterResponseDTO;
import com.shiftplanner.service.RosterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rosters")
@Tag(name = "Weekly Roster Management", description = "Endpoints for roster assignments")
public class RosterController {

    private final RosterService rosterService;

    public RosterController(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    @GetMapping
    @Operation(summary = "Get all roster assignments")
    public ResponseEntity<List<RosterDTO>> getAllRosters() {
        return ResponseEntity.ok(rosterService.getAllRosters());
    }

    @GetMapping("/weekly")
    @Operation(summary = "Get weekly roster view")
    public ResponseEntity<WeeklyRosterResponseDTO> getWeeklyRoster(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return ResponseEntity.ok(rosterService.getWeeklyRoster(startDate));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get roster assignments for a specific employee")
    public ResponseEntity<List<RosterDTO>> getRostersByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(rosterService.getRostersByEmployeeId(employeeId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get roster assignment by ID")
    public ResponseEntity<RosterDTO> getRosterById(@PathVariable Long id) {
        return ResponseEntity.ok(rosterService.getRosterById(id));
    }

    @PostMapping
    @Operation(summary = "Assign employee to a shift (Create Roster)")
    public ResponseEntity<RosterDTO> createRoster(@Valid @RequestBody CreateRosterDTO createRosterDTO) {
        RosterDTO created = rosterService.createRoster(createRosterDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update roster assignment")
    public ResponseEntity<RosterDTO> updateRoster(@PathVariable Long id, @Valid @RequestBody CreateRosterDTO createRosterDTO) {
        return ResponseEntity.ok(rosterService.updateRoster(id, createRosterDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete / cancel roster assignment")
    public ResponseEntity<Void> deleteRoster(@PathVariable Long id) {
        rosterService.deleteRoster(id);
        return ResponseEntity.noContent().build();
    }
}
