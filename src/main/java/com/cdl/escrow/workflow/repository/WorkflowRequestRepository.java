package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.workflow.dto.MyEngagementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestRepository extends JpaRepository<WorkflowRequest,Long>, JpaSpecificationExecutor<WorkflowRequest> {

    @Query("""
       select new com.cdl.escrow.workflow.dto.MyEngagementDTO(
          r.id,
          r.referenceId,
          r.moduleName,
          r.actionKey,
          r.taskStatus.code,
          r.currentStageOrder,
          s.stageKey,
          s.taskStatus.code,
          case when r.createdBy = :userId then 'CREATOR' else 'APPROVER' end,
          coalesce(max(a.decidedAt), r.lastUpdatedAt, r.createdAt),
          r.payloadJson
       )
       from WorkflowRequest r
       left join r.workflowRequestStages s on s.stageOrder = r.currentStageOrder
       left join s.workflowRequestStageApprovals a
       where (:makersOnly = true and r.createdBy = :userId)
          or (:actorsOnly = true and exists (
                select 1 from WorkflowRequestStageApproval a2
                join a2.workflowRequestStage st2
                where st2.workflowRequest = r and a2.approverUserId = :userId
              ))
          or (:allRoles = true and (
                r.createdBy = :userId or exists (
                   select 1 from WorkflowRequestStageApproval a3
                   join a3.workflowRequestStage st3
                   where st3.workflowRequest = r and a3.approverUserId = :userId
                )
              ))
          and (:moduleName is null or r.moduleName = :moduleName)
       group by r.id, r.referenceId, r.moduleName, r.actionKey, r.taskStatus.code,
                r.currentStageOrder, s.stageKey, s.taskStatus.code,
                r.createdBy, r.lastUpdatedAt, r.createdAt, r.payloadJson
       """)
    Page<MyEngagementDTO> findMyEngagements(
            @Param("userId") String userId,
            @Param("makersOnly") boolean makersOnly,
            @Param("actorsOnly") boolean actorsOnly, // checker/approver/reviewer
            @Param("allRoles") boolean allRoles,
            @Param("moduleName") String moduleName,
            Pageable pageable
    );

}
