package com.example.shiftplanner.dto;

import com.shiftplanner.enums.SwapRequestStatus;

import java.time.LocalDateTime;

public class SwapRequestDTO {
    private Long id;

    private Long requesterId;
    private String requesterName;
    private String requesterEmail;

    private Long colleagueId;
    private String colleagueName;
    private String colleagueEmail;

    private Long rosterId;
    private String shiftName;
    private String shiftDate;
    private String shiftStartTime;
    private String shiftEndTime;

    private Long requestedShiftId;
    private String requestedShiftName;

    private String reason;
    private SwapRequestStatus status;

    private Boolean colleagueApproval;
    private Boolean managerApproval;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SwapRequestDTO() {}

    public SwapRequestDTO(Long id, Long requesterId, String requesterName, String requesterEmail, Long colleagueId, String colleagueName, String colleagueEmail, Long rosterId, String shiftName, String shiftDate, String shiftStartTime, String shiftEndTime, Long requestedShiftId, String requestedShiftName, String reason, SwapRequestStatus status, Boolean colleagueApproval, Boolean managerApproval, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.requesterEmail = requesterEmail;
        this.colleagueId = colleagueId;
        this.colleagueName = colleagueName;
        this.colleagueEmail = colleagueEmail;
        this.rosterId = rosterId;
        this.shiftName = shiftName;
        this.shiftDate = shiftDate;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        this.requestedShiftId = requestedShiftId;
        this.requestedShiftName = requestedShiftName;
        this.reason = reason;
        this.status = status;
        this.colleagueApproval = colleagueApproval;
        this.managerApproval = managerApproval;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long requesterId;
        private String requesterName;
        private String requesterEmail;
        private Long colleagueId;
        private String colleagueName;
        private String colleagueEmail;
        private Long rosterId;
        private String shiftName;
        private String shiftDate;
        private String shiftStartTime;
        private String shiftEndTime;
        private Long requestedShiftId;
        private String requestedShiftName;
        private String reason;
        private SwapRequestStatus status;
        private Boolean colleagueApproval;
        private Boolean managerApproval;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder requesterId(Long requesterId) { this.requesterId = requesterId; return this; }
        public Builder requesterName(String requesterName) { this.requesterName = requesterName; return this; }
        public Builder requesterEmail(String requesterEmail) { this.requesterEmail = requesterEmail; return this; }
        public Builder colleagueId(Long colleagueId) { this.colleagueId = colleagueId; return this; }
        public Builder colleagueName(String colleagueName) { this.colleagueName = colleagueName; return this; }
        public Builder colleagueEmail(String colleagueEmail) { this.colleagueEmail = colleagueEmail; return this; }
        public Builder rosterId(Long rosterId) { this.rosterId = rosterId; return this; }
        public Builder shiftName(String shiftName) { this.shiftName = shiftName; return this; }
        public Builder shiftDate(String shiftDate) { this.shiftDate = shiftDate; return this; }
        public Builder shiftStartTime(String shiftStartTime) { this.shiftStartTime = shiftStartTime; return this; }
        public Builder shiftEndTime(String shiftEndTime) { this.shiftEndTime = shiftEndTime; return this; }
        public Builder requestedShiftId(Long requestedShiftId) { this.requestedShiftId = requestedShiftId; return this; }
        public Builder requestedShiftName(String requestedShiftName) { this.requestedShiftName = requestedShiftName; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder status(SwapRequestStatus status) { this.status = status; return this; }
        public Builder colleagueApproval(Boolean colleagueApproval) { this.colleagueApproval = colleagueApproval; return this; }
        public Builder managerApproval(Boolean managerApproval) { this.managerApproval = managerApproval; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public SwapRequestDTO build() {
            return new SwapRequestDTO(id, requesterId, requesterName, requesterEmail, colleagueId, colleagueName, colleagueEmail, rosterId, shiftName, shiftDate, shiftStartTime, shiftEndTime, requestedShiftId, requestedShiftName, reason, status, colleagueApproval, managerApproval, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }

    public String getRequesterName() { return requesterName; }
    public void setRequesterName(String requesterName) { this.requesterName = requesterName; }

    public String getRequesterEmail() { return requesterEmail; }
    public void setRequesterEmail(String requesterEmail) { this.requesterEmail = requesterEmail; }

    public Long getColleagueId() { return colleagueId; }
    public void setColleagueId(Long colleagueId) { this.colleagueId = colleagueId; }

    public String getColleagueName() { return colleagueName; }
    public void setColleagueName(String colleagueName) { this.colleagueName = colleagueName; }

    public String getColleagueEmail() { return colleagueEmail; }
    public void setColleagueEmail(String colleagueEmail) { this.colleagueEmail = colleagueEmail; }

    public Long getRosterId() { return rosterId; }
    public void setRosterId(Long rosterId) { this.rosterId = rosterId; }

    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }

    public String getShiftDate() { return shiftDate; }
    public void setShiftDate(String shiftDate) { this.shiftDate = shiftDate; }

    public String getShiftStartTime() { return shiftStartTime; }
    public void setShiftStartTime(String shiftStartTime) { this.shiftStartTime = shiftStartTime; }

    public String getShiftEndTime() { return shiftEndTime; }
    public void setShiftEndTime(String shiftEndTime) { this.shiftEndTime = shiftEndTime; }

    public Long getRequestedShiftId() { return requestedShiftId; }
    public void setRequestedShiftId(Long requestedShiftId) { this.requestedShiftId = requestedShiftId; }

    public String getRequestedShiftName() { return requestedShiftName; }
    public void setRequestedShiftName(String requestedShiftName) { this.requestedShiftName = requestedShiftName; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public SwapRequestStatus getStatus() { return status; }
    public void setStatus(SwapRequestStatus status) { this.status = status; }

    public Boolean getColleagueApproval() { return colleagueApproval; }
    public void setColleagueApproval(Boolean colleagueApproval) { this.colleagueApproval = colleagueApproval; }

    public Boolean getManagerApproval() { return managerApproval; }
    public void setManagerApproval(Boolean managerApproval) { this.managerApproval = managerApproval; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
