package com.api.rest.enums;

public enum TaskStatus {
    CREATED,
    APPROVED,
    REJECTED,
    BLOCKED,
    DONE;

    public static boolean isValidStatus(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}