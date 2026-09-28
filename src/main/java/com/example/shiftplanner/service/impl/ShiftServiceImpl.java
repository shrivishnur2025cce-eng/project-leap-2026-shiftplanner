package com.example.shiftplanner.service.impl;

import com.shiftplanner.dto.ShiftDTO;
import com.shiftplanner.entity.Shift;
import com.shiftplanner.exception.ShiftNotFoundException;
import com.shiftplanner.mapper.DtoMapper;
import com.shiftplanner.repository.ShiftRepository;
import com.shiftplanner.service.ShiftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final DtoMapper dtoMapper;

    public ShiftServiceImpl(ShiftRepository shiftRepository, DtoMapper dtoMapper) {
        this.shiftRepository = shiftRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShiftDTO> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(dtoMapper::toShiftDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ShiftDTO getShiftById(Long id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new ShiftNotFoundException("Shift not found with id: " + id));
        return dtoMapper.toShiftDTO(shift);
    }

    @Override
    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        if (shiftDTO.getStartTime().isAfter(shiftDTO.getEndTime()) || shiftDTO.getStartTime().equals(shiftDTO.getEndTime())) {
            throw new IllegalArgumentException("Shift start time must be strictly before end time");
        }
        Shift shift = dtoMapper.toShiftEntity(shiftDTO);
        shift.setId(null);
        Shift saved = shiftRepository.save(shift);
        return dtoMapper.toShiftDTO(saved);
    }

    @Override
    public ShiftDTO updateShift(Long id, ShiftDTO shiftDTO) {
        Shift existing = shiftRepository.findById(id)
                .orElseThrow(() -> new ShiftNotFoundException("Shift not found with id: " + id));

        if (shiftDTO.getStartTime().isAfter(shiftDTO.getEndTime()) || shiftDTO.getStartTime().equals(shiftDTO.getEndTime())) {
            throw new IllegalArgumentException("Shift start time must be strictly before end time");
        }

        existing.setShiftName(shiftDTO.getShiftName());
        existing.setDate(shiftDTO.getDate());
        existing.setStartTime(shiftDTO.getStartTime());
        existing.setEndTime(shiftDTO.getEndTime());

        Shift updated = shiftRepository.save(existing);
        return dtoMapper.toShiftDTO(updated);
    }

    @Override
    public void deleteShift(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new ShiftNotFoundException("Shift not found with id: " + id);
        }
        shiftRepository.deleteById(id);
    }
}
