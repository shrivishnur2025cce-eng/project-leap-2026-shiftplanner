package com.example.shiftplanner.config;

import com.shiftplanner.entity.Employee;
import com.shiftplanner.entity.Roster;
import com.shiftplanner.entity.Shift;
import com.shiftplanner.enums.Role;
import com.shiftplanner.enums.RosterStatus;
import com.shiftplanner.repository.EmployeeRepository;
import com.shiftplanner.repository.RosterRepository;
import com.shiftplanner.repository.ShiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final RosterRepository rosterRepository;

    public DataInitializer(EmployeeRepository employeeRepository, ShiftRepository shiftRepository, RosterRepository rosterRepository) {
        this.employeeRepository = employeeRepository;
        this.shiftRepository = shiftRepository;
        this.rosterRepository = rosterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() > 0) {
            return; // Data already initialized
        }

        // 1. Create Employees
        Employee arun = Employee.builder().name("Arun").email("arun@shiftplanner.com").role(Role.EMPLOYEE).active(true).build();
        Employee kumar = Employee.builder().name("Kumar").email("kumar@shiftplanner.com").role(Role.EMPLOYEE).active(true).build();
        Employee priya = Employee.builder().name("Priya").email("priya@shiftplanner.com").role(Role.EMPLOYEE).active(true).build();
        Employee ravi = Employee.builder().name("Ravi").email("ravi@shiftplanner.com").role(Role.EMPLOYEE).active(true).build();
        Employee manager = Employee.builder().name("Manager").email("manager@shiftplanner.com").role(Role.MANAGER).active(true).build();

        employeeRepository.saveAll(Arrays.asList(arun, kumar, priya, ravi, manager));

        // 2. Create Shifts for current week (Monday to Sunday)
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalTime morningStart = LocalTime.of(8, 0);
        LocalTime morningEnd = LocalTime.of(16, 0);

        LocalTime eveningStart = LocalTime.of(16, 0);
        LocalTime eveningEnd = LocalTime.of(23, 59);

        LocalTime nightStart = LocalTime.of(0, 0);
        LocalTime nightEnd = LocalTime.of(8, 0);

        for (int i = 0; i < 7; i++) {
            LocalDate date = monday.plusDays(i);
            Shift morningShift = Shift.builder().shiftName("Morning Shift").date(date).startTime(morningStart).endTime(morningEnd).build();
            Shift eveningShift = Shift.builder().shiftName("Evening Shift").date(date).startTime(eveningStart).endTime(eveningEnd).build();
            Shift nightShift = Shift.builder().shiftName("Night Shift").date(date).startTime(nightStart).endTime(nightEnd).build();

            List<Shift> savedShifts = shiftRepository.saveAll(Arrays.asList(morningShift, eveningShift, nightShift));

            if (i < 5) {
                rosterRepository.save(Roster.builder().employee(arun).shift(savedShifts.get(0)).status(RosterStatus.ASSIGNED).build());
                rosterRepository.save(Roster.builder().employee(kumar).shift(savedShifts.get(1)).status(RosterStatus.ASSIGNED).build());
                rosterRepository.save(Roster.builder().employee(priya).shift(savedShifts.get(2)).status(RosterStatus.ASSIGNED).build());
                if (i % 2 == 0) {
                    rosterRepository.save(Roster.builder().employee(ravi).shift(savedShifts.get(1)).status(RosterStatus.ASSIGNED).build());
                } else {
                    rosterRepository.save(Roster.builder().employee(ravi).shift(savedShifts.get(0)).status(RosterStatus.ASSIGNED).build());
                }
            }
        }
    }
}
