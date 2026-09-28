package com.example.shiftplanner.service.impl;

import com.shiftplanner.dto.CreateSwapRequestDTO;
import com.shiftplanner.dto.SwapRequestDTO;
import com.shiftplanner.entity.Employee;
import com.shiftplanner.entity.Roster;
import com.shiftplanner.entity.Shift;
import com.shiftplanner.entity.SwapRequest;
import com.shiftplanner.enums.RosterStatus;
import com.shiftplanner.enums.SwapRequestStatus;
import com.shiftplanner.exception.*;
import com.shiftplanner.mapper.DtoMapper;
import com.shiftplanner.repository.EmployeeRepository;
import com.shiftplanner.repository.RosterRepository;
import com.shiftplanner.repository.ShiftRepository;
import com.shiftplanner.repository.SwapRequestRepository;
import com.shiftplanner.service.SwapRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SwapRequestServiceImpl implements SwapRequestService {

    private final SwapRequestRepository swapRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final RosterRepository rosterRepository;
    private final ShiftRepository shiftRepository;
    private final DtoMapper dtoMapper;

    public SwapRequestServiceImpl(SwapRequestRepository swapRequestRepository, EmployeeRepository employeeRepository, RosterRepository rosterRepository, ShiftRepository shiftRepository, DtoMapper dtoMapper) {
        this.swapRequestRepository = swapRequestRepository;
        this.employeeRepository = employeeRepository;
        this.rosterRepository = rosterRepository;
        this.shiftRepository = shiftRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SwapRequestDTO> getAllSwapRequests() {
        return swapRequestRepository.findAll().stream()
                .map(dtoMapper::toSwapRequestDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SwapRequestDTO getSwapRequestById(Long id) {
        SwapRequest request = swapRequestRepository.findById(id)
                .orElseThrow(() -> new SwapRequestNotFoundException("Swap request not found with id: " + id));
        return dtoMapper.toSwapRequestDTO(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SwapRequestDTO> getSwapRequestsByEmployeeId(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
        return swapRequestRepository.findByEmployeeId(employeeId).stream()
                .map(dtoMapper::toSwapRequestDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SwapRequestDTO> getPendingSwapRequests() {
        return swapRequestRepository.findAllPendingRequests().stream()
                .map(dtoMapper::toSwapRequestDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SwapRequestDTO createSwapRequest(CreateSwapRequestDTO createDto) {
        Employee requester = employeeRepository.findById(createDto.getRequesterId())
                .orElseThrow(() -> new EmployeeNotFoundException("Requester not found with id: " + createDto.getRequesterId()));

        Employee colleague = employeeRepository.findById(createDto.getColleagueId())
                .orElseThrow(() -> new EmployeeNotFoundException("Colleague not found with id: " + createDto.getColleagueId()));

        if (!requester.isActive()) {
            throw new InvalidSwapException("Cannot create swap request for inactive requester: " + requester.getName());
        }
        if (!colleague.isActive()) {
            throw new InvalidSwapException("Cannot request swap with inactive colleague: " + colleague.getName());
        }

        if (requester.getId().equals(colleague.getId())) {
            throw new InvalidSwapException("Requester and colleague cannot be the same employee");
        }

        Roster roster = rosterRepository.findById(createDto.getRosterId())
                .orElseThrow(() -> new RosterNotFoundException("Roster assignment not found with id: " + createDto.getRosterId()));

        if (roster.getStatus() != RosterStatus.ASSIGNED) {
            throw new InvalidSwapException("Cannot swap a cancelled or inactive roster assignment");
        }

        if (!roster.getEmployee().getId().equals(requester.getId())) {
            throw new UnauthorizedSwapOperationException("Employee " + requester.getName() + " is not assigned to roster assignment id: " + roster.getId());
        }

        List<SwapRequest> activePending = swapRequestRepository.findActivePendingRequestsForRoster(roster.getId());
        if (!activePending.isEmpty()) {
            throw new InvalidSwapException("A pending swap request already exists for this roster assignment");
        }

        Shift requestedShift = null;
        if (createDto.getRequestedShiftId() != null) {
            requestedShift = shiftRepository.findById(createDto.getRequestedShiftId())
                    .orElseThrow(() -> new ShiftNotFoundException("Requested target shift not found with id: " + createDto.getRequestedShiftId()));
        }

        SwapRequest request = SwapRequest.builder()
                .requester(requester)
                .colleague(colleague)
                .roster(roster)
                .requestedShift(requestedShift)
                .reason(createDto.getReason())
                .status(SwapRequestStatus.PENDING_COLLEAGUE)
                .colleagueApproval(null)
                .managerApproval(null)
                .build();

        SwapRequest saved = swapRequestRepository.save(request);
        return dtoMapper.toSwapRequestDTO(saved);
    }

    @Override
    public SwapRequestDTO colleagueApprove(Long id) {
        SwapRequest request = swapRequestRepository.findById(id)
                .orElseThrow(() -> new SwapRequestNotFoundException("Swap request not found with id: " + id));

        if (request.getStatus() != SwapRequestStatus.PENDING_COLLEAGUE) {
            throw new InvalidSwapException("Swap request cannot be approved by colleague in its current status: " + request.getStatus());
        }

        request.setStatus(SwapRequestStatus.COLLEAGUE_APPROVED);
        request.setColleagueApproval(true);

        SwapRequest updated = swapRequestRepository.save(request);
        return dtoMapper.toSwapRequestDTO(updated);
    }

    @Override
    public SwapRequestDTO colleagueReject(Long id) {
        SwapRequest request = swapRequestRepository.findById(id)
                .orElseThrow(() -> new SwapRequestNotFoundException("Swap request not found with id: " + id));

        if (request.getStatus() != SwapRequestStatus.PENDING_COLLEAGUE) {
            throw new InvalidSwapException("Swap request cannot be rejected by colleague in its current status: " + request.getStatus());
        }

        request.setStatus(SwapRequestStatus.COLLEAGUE_REJECTED);
        request.setColleagueApproval(false);

        SwapRequest updated = swapRequestRepository.save(request);
        return dtoMapper.toSwapRequestDTO(updated);
    }

    @Override
    public SwapRequestDTO managerApprove(Long id) {
        SwapRequest request = swapRequestRepository.findById(id)
                .orElseThrow(() -> new SwapRequestNotFoundException("Swap request not found with id: " + id));

        if (request.getStatus() != SwapRequestStatus.COLLEAGUE_APPROVED || Boolean.TRUE != request.getColleagueApproval()) {
            throw new InvalidSwapException("Manager cannot approve swap request before colleague approval (Current status: " + request.getStatus() + ")");
        }

        Roster roster = request.getRoster();
        if (roster == null || roster.getStatus() != RosterStatus.ASSIGNED) {
            request.setStatus(SwapRequestStatus.CANCELLED);
            swapRequestRepository.save(request);
            throw new InvalidSwapException("The underlying roster assignment no longer exists or has been cancelled");
        }

        Employee requester = request.getRequester();
        Employee colleague = request.getColleague();
        Shift shiftToTransfer = roster.getShift();

        boolean colleagueHasOverlap = rosterRepository.existsOverlappingShift(
                colleague.getId(),
                shiftToTransfer.getDate(),
                shiftToTransfer.getStartTime(),
                shiftToTransfer.getEndTime(),
                roster.getId()
        );

        if (colleagueHasOverlap) {
            throw new ShiftOverlapException("Colleague " + colleague.getName() + " already has an overlapping shift on " + shiftToTransfer.getDate());
        }

        Optional<Roster> colleagueRosterOpt = Optional.empty();
        if (request.getRequestedShift() != null) {
            colleagueRosterOpt = rosterRepository.findByEmployeeIdAndShiftIdAndStatus(
                    colleague.getId(), request.getRequestedShift().getId(), RosterStatus.ASSIGNED);
        } else {
            List<Roster> colleagueRostersOnDate = rosterRepository.findAssignedRostersForEmployeeOnDate(
                    colleague.getId(), shiftToTransfer.getDate());
            if (!colleagueRostersOnDate.isEmpty()) {
                colleagueRosterOpt = Optional.of(colleagueRostersOnDate.get(0));
            }
        }

        if (colleagueRosterOpt.isPresent()) {
            Roster colleagueRoster = colleagueRosterOpt.get();
            Shift colleagueShift = colleagueRoster.getShift();

            boolean requesterHasOverlap = rosterRepository.existsOverlappingShift(
                    requester.getId(),
                    colleagueShift.getDate(),
                    colleagueShift.getStartTime(),
                    colleagueShift.getEndTime(),
                    colleagueRoster.getId()
            );

            if (requesterHasOverlap) {
                throw new ShiftOverlapException("Requester " + requester.getName() + " already has an overlapping shift on " + colleagueShift.getDate());
            }

            roster.setEmployee(colleague);
            colleagueRoster.setEmployee(requester);

            rosterRepository.save(roster);
            rosterRepository.save(colleagueRoster);
        } else {
            roster.setEmployee(colleague);
            rosterRepository.save(roster);
        }

        request.setStatus(SwapRequestStatus.MANAGER_APPROVED);
        request.setManagerApproval(true);

        SwapRequest updated = swapRequestRepository.save(request);
        return dtoMapper.toSwapRequestDTO(updated);
    }

    @Override
    public SwapRequestDTO managerReject(Long id) {
        SwapRequest request = swapRequestRepository.findById(id)
                .orElseThrow(() -> new SwapRequestNotFoundException("Swap request not found with id: " + id));

        if (request.getStatus() == SwapRequestStatus.MANAGER_APPROVED || request.getStatus() == SwapRequestStatus.MANAGER_REJECTED) {
            throw new InvalidSwapException("Swap request has already been finalized by manager with status: " + request.getStatus());
        }

        request.setStatus(SwapRequestStatus.MANAGER_REJECTED);
        request.setManagerApproval(false);

        SwapRequest updated = swapRequestRepository.save(request);
        return dtoMapper.toSwapRequestDTO(updated);
    }
}
