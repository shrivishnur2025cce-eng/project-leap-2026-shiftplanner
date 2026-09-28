package com.example.shiftplanner.service;

import com.shiftplanner.dto.CreateSwapRequestDTO;
import com.shiftplanner.dto.SwapRequestDTO;

import java.util.List;

public interface SwapRequestService {
    List<SwapRequestDTO> getAllSwapRequests();
    SwapRequestDTO getSwapRequestById(Long id);
    List<SwapRequestDTO> getSwapRequestsByEmployeeId(Long employeeId);
    List<SwapRequestDTO> getPendingSwapRequests();
    SwapRequestDTO createSwapRequest(CreateSwapRequestDTO createDto);
    SwapRequestDTO colleagueApprove(Long id);
    SwapRequestDTO colleagueReject(Long id);
    SwapRequestDTO managerApprove(Long id);
    SwapRequestDTO managerReject(Long id);
}
