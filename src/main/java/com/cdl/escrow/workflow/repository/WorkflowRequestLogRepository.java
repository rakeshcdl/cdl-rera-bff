package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkflowRequestLogRepository extends JpaRepository<WorkflowRequestLog,Long>, JpaSpecificationExecutor<WorkflowRequestLog> {
}
