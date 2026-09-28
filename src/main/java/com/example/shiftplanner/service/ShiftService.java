package com.example.shiftplanner.service;

import com.shiftplanner.dto.ShiftDTO;

import java.util.List;

public interface ShiftService {
    List<ShiftDTO> getAllShifts();
    ShiftDTO getShiftById(Long id);
    ShiftDTO createShift(ShiftDTO shiftDTO);
    ShiftDTO updateShift(Long id, ShiftDTO shiftDTO);
    void deleteShift(Long id);
}
