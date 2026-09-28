package com.example.shiftplanner.service.impl;

import com.shiftplanner.dto.EmployeeDTO;
import com.shiftplanner.entity.Employee;
import com.shiftplanner.exception.EmployeeNotFoundException;
import com.shiftplanner.mapper.DtoMapper;
import com.shiftplanner.repository.EmployeeRepository;
import com.shiftplanner.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DtoMapper dtoMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DtoMapper dtoMapper) {
        this.employeeRepository = employeeRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(dtoMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return dtoMapper.toEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.findByEmail(employeeDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Employee with email " + employeeDTO.getEmail() + " already exists");
        }
        Employee employee = dtoMapper.toEmployeeEntity(employeeDTO);
        employee.setId(null);
        Employee saved = employeeRepository.save(employee);
        return dtoMapper.toEmployeeDTO(saved);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        existing.setName(employeeDTO.getName());
        existing.setEmail(employeeDTO.getEmail());
        existing.setRole(employeeDTO.getRole());
        existing.setActive(employeeDTO.isActive());

        Employee updated = employeeRepository.save(existing);
        return dtoMapper.toEmployeeDTO(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        existing.setActive(false);
        employeeRepository.save(existing);
    }
}
