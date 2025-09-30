package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowStageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowStageTemplateRepository extends JpaRepository<WorkflowStageTemplate,Long>, JpaSpecificationExecutor<WorkflowStageTemplate> {
    List<WorkflowStageTemplate> findByWorkflowDefinitionIdOrderByStageOrder(Long workflowDefinitionId);

    List<WorkflowStageTemplate> findByWorkflowDefinitionId(Long id);
}

