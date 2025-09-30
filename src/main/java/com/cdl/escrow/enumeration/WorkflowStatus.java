package com.cdl.escrow.enumeration;

public enum WorkflowStatus {
    INITIATED,     // Workflow is created, waiting for approvals
    IN_PROGRESS,   // Some steps approved, some pending
    APPROVED,      // All required steps approved
    REJECTED,      // Rejected by any approver
    CANCELLED,      // Manually cancelled or voided
    DRAFT,
    ON_HOLD,
    ACTIVE,
    INACTIVE
}
