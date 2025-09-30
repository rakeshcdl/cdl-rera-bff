package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.workflow.dto.ext.AwaitingActionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRequestStageRepository extends JpaRepository<WorkflowRequestStage,Long>, JpaSpecificationExecutor<WorkflowRequestStage> {


    @Query("""
       select new com.cdl.escrow.workflow.dto.ext.AwaitingActionDTO(
          r.id, r.referenceId, r.moduleName, r.actionKey, r.createdBy, r.createdAt,
          s.id, s.stageOrder, s.stageKey, s.keycloakGroup,
          s.requiredApprovals, coalesce(s.approvalsObtained, 0),
          (s.requiredApprovals - coalesce(s.approvalsObtained, 0)),
          r.payloadJson
       )
       from WorkflowRequestStage s
       join s.workflowRequest r
       where s.stageOrder = r.currentStageOrder
         and s.taskStatus.code in :activeStageCodes
         and r.taskStatus.code in :activeRequestCodes
         and ( upper(s.keycloakGroup) in :groups or upper(function('replace', s.keycloakGroup, '/', '_')) in :groups )
         and coalesce(s.approvalsObtained, 0) < s.requiredApprovals
         and (:moduleName is null or upper(r.moduleName) = upper(:moduleName))
       """)
    Page<AwaitingActionDTO> findAwaitingForGroups(
            @Param("groups") java.util.Collection<String> groups,
            @Param("activeStageCodes") java.util.Collection<String> activeStageCodes,
            @Param("activeRequestCodes") java.util.Collection<String> activeRequestCodes,
            @Param("moduleName") String moduleName,
            Pageable pageable
    );

}
