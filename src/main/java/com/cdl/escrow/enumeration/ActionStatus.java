package com.cdl.escrow.enumeration;

public enum ActionStatus {
    PENDING,       // Waiting for action
    APPROVED,      // Approved by assigned user/group
    REJECTED,      // Rejected by user/group
    SKIPPED        // Skipped due to conditional logic (optional)
}
