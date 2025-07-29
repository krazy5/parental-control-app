package com.example.parental_control_system.dto;

public class CommandResultMessage {
    private Long commandId;
    private boolean success;
    private String message;

    // Getters & setters (or use Lombok @Data)
    public Long getCommandId() {
        return commandId;
    }
    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
