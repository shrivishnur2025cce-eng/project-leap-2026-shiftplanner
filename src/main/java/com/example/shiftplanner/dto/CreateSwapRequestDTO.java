package com.example.shiftplanner.dto;

import jakarta.validation.constraints.NotNull;

public class CreateSwapRequestDTO {

    @NotNull(message = "Requester ID is required")
    private Long requesterId;

    @NotNull(message = "Colleague ID is required")
    private Long colleagueId;

    @NotNull(message = "Roster ID is required")
    private Long rosterId;

    private Long requestedShiftId;

    private String reason;

    public CreateSwapRequestDTO() {}

    public CreateSwapRequestDTO(Long requesterId, Long colleagueId, Long rosterId, Long requestedShiftId, String reason) {
        this.requesterId = requesterId;
        this.colleagueId = colleagueId;
        this.rosterId = rosterId;
        this.requestedShiftId = requestedShiftId;
        this.reason = reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long requesterId;
        private Long colleagueId;
        private Long rosterId;
        private Long requestedShiftId;
        private String reason;

        public Builder requesterId(Long requesterId) { this.requesterId = requesterId; return this; }
        public Builder colleagueId(Long colleagueId) { this.colleagueId = colleagueId; return this; }
        public Builder rosterId(Long rosterId) { this.rosterId = rosterId; return this; }
        public Builder requestedShiftId(Long requestedShiftId) { this.requestedShiftId = requestedShiftId; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }

        public CreateSwapRequestDTO build() {
            return new CreateSwapRequestDTO(requesterId, colleagueId, rosterId, requestedShiftId, reason);
        }
    }

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }

    public Long getColleagueId() { return colleagueId; }
    public void setColleagueId(Long colleagueId) { this.colleagueId = colleagueId; }

    public Long getRosterId() { return rosterId; }
    public void setRosterId(Long rosterId) { this.rosterId = rosterId; }

    public Long getRequestedShiftId() { return requestedShiftId; }
    public void setRequestedShiftId(Long requestedShiftId) { this.requestedShiftId = requestedShiftId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
