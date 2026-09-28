package com.example.shiftplanner.repository;

import com.shiftplanner.entity.Employee;
import com.shiftplanner.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findByActive(boolean active);
    List<Employee> findByRole(Role role);
}
