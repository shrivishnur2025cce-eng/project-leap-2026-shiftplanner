package com.example.shiftplanner.controller;

import com.shiftplanner.dto.CreateSwapRequestDTO;
import com.shiftplanner.dto.SwapRequestDTO;
import com.shiftplanner.service.SwapRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swap-requests")
@Tag(name = "Shift Swap Requests", description = "Endpoints for managing shift swap requests")
public class SwapRequestController {

    private final SwapRequestService swapRequestService;

    public SwapRequestController(SwapRequestService swapRequestService) {
        this.swapRequestService = swapRequestService;
    }

    @GetMapping
    @Operation(summary = "Get all swap requests")
    public ResponseEntity<List<SwapRequestDTO>> getAllSwapRequests() {
        return ResponseEntity.ok(swapRequestService.getAllSwapRequests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get swap request by ID")
    public ResponseEntity<SwapRequestDTO> getSwapRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.getSwapRequestById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get swap requests for an employee (as requester or colleague)")
    public ResponseEntity<List<SwapRequestDTO>> getSwapRequestsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(swapRequestService.getSwapRequestsByEmployeeId(employeeId));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending swap requests for manager review")
    public ResponseEntity<List<SwapRequestDTO>> getPendingSwapRequests() {
        return ResponseEntity.ok(swapRequestService.getPendingSwapRequests());
    }

    @PostMapping
    @Operation(summary = "Create a new shift swap request")
    public ResponseEntity<SwapRequestDTO> createSwapRequest(@Valid @RequestBody CreateSwapRequestDTO createDto) {
        SwapRequestDTO created = swapRequestService.createSwapRequest(createDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/colleague-approve")
    @Operation(summary = "Colleague approves a swap request")
    public ResponseEntity<SwapRequestDTO> colleagueApprove(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.colleagueApprove(id));
    }

    @PutMapping("/{id}/colleague-reject")
    @Operation(summary = "Colleague rejects a swap request")
    public ResponseEntity<SwapRequestDTO> colleagueReject(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.colleagueReject(id));
    }

    @PutMapping("/{id}/manager-approve")
    @Operation(summary = "Manager approves a swap request (applies shift swap)")
    public ResponseEntity<SwapRequestDTO> managerApprove(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.managerApprove(id));
    }

    @PutMapping("/{id}/manager-reject")
    @Operation(summary = "Manager rejects a swap request")
    public ResponseEntity<SwapRequestDTO> managerReject(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.managerReject(id));
    }
}
