package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WorkflowRequestLogRepository extends JpaRepository<WorkflowRequestLog,Long>, JpaSpecificationExecutor<WorkflowRequestLog> {
    List<WorkflowRequestLog> findByWorkflowRequest_Id(Long requestId);

}
