package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequestStage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowRequestStageRepository
        extends JpaRepository<WorkflowRequestStage, Long>, JpaSpecificationExecutor<WorkflowRequestStage> {

    // --- “Pending” by groups + module (assumes pending = completedAt IS NULL) ---
    List<WorkflowRequestStage> findByKeycloakGroupInAndWorkflowRequest_ModuleNameAndCompletedAtIsNull(
            List<String> userGroups, String moduleName);

    // “Pending” by groups only
    List<WorkflowRequestStage> findByKeycloakGroupInAndCompletedAtIsNull(List<String> userGroups);

    // --- By request id (correct traversal via relationship) ---
    List<WorkflowRequestStage> findByWorkflowRequest_Id(Long requestId);

    Optional<WorkflowRequestStage> findByWorkflowRequest_IdAndStageOrder(Long requestId, Integer stageOrder);

    // ---------- OPTIONAL (use if you mark pending via TaskStatus) ----------
    // e.g., TaskStatus has a field `statusKey` with values like "PENDING"
   /* List<WorkflowRequestStage> findByKeycloakGroupInAndWorkflowRequest_ModuleNameAndTaskStatus_Code(
            List<String> userGroups, String moduleName, String statusKey);

    List<WorkflowRequestStage> findByKeycloakGroupInAndTaskStatus_StatusKey(
            List<String> userGroups, String statusKey);*/

    // If you often need approvals eager-loaded, you can add:
    @EntityGraph(attributePaths = "workflowRequestStageApprovals")
    List<WorkflowRequestStage> findAllByWorkflowRequest_Id(Long requestId);

    List<WorkflowRequestStage> findByKeycloakGroupInAndWorkflowRequest_ModuleName(List<String> userGroups, String moduleName);

    List<WorkflowRequestStage> findByKeycloakGroupIn(List<String> userGroups);

}

