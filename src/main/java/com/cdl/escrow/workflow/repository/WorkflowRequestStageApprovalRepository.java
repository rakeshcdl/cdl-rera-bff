package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequestStageApproval;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRequestStageApprovalRepository
        extends JpaRepository<WorkflowRequestStageApproval, Long>,
        JpaSpecificationExecutor<WorkflowRequestStageApproval> {

    // was: existsByStageIdAndUserId(...)
    boolean existsByWorkflowRequestStage_IdAndApproverUserId(Long stageId, String approverUserId);

    // was: findByUserIdAndModule(...)
    // Requires: WorkflowRequestStage -> WorkflowRequest -> moduleName (field must exist on WorkflowRequest)
    List<WorkflowRequestStageApproval> findByApproverUserIdAndWorkflowRequestStage_WorkflowRequest_ModuleName(
            String approverUserId, String moduleName);

    // was: findByUserId(...)
    List<WorkflowRequestStageApproval> findByApproverUserId(String approverUserId);

    // was: findByStageId(...)
    List<WorkflowRequestStageApproval> findByWorkflowRequestStage_Id(Long stageId);

    // ------- Optional & handy variants -------

   /* // by username
    List<WorkflowRequestStageApproval> findByApproverUsername(String approverUsername);

    // by group
    List<WorkflowRequestStageApproval> findByApproverGroup(String approverGroup);

    // filter by TaskStatus statusKey (if TaskStatus has 'statusKey')
   *//* List<WorkflowRequestStageApproval> findByApproverUserIdAndTaskStatus_StatusKey(
            String approverUserId, String statusKey);*//*

    // eager-load the stage (and optionally its request) to avoid N+1
    @EntityGraph(attributePaths = { "workflowRequestStage" })
    List<WorkflowRequestStageApproval> findAllByWorkflowRequestStage_Id(Long stageId);

    // if you often need the request too:
    @EntityGraph(attributePaths = { "workflowRequestStage", "workflowRequestStage.workflowRequest" })
    List<WorkflowRequestStageApproval> findAllByApproverUserId(String approverUserId);*/
}


