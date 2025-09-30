package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequestStageApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestStageApprovalRepository extends JpaRepository<WorkflowRequestStageApproval,Long>, JpaSpecificationExecutor<WorkflowRequestStageApproval> {

   // long countByWorkflowRequestStageAndWorkflowDecision(WorkflowRequestStage stage, WorkflowDecision decision);

}

