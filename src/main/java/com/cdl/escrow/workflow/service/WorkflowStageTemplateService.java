package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.WorkflowStageTemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkflowStageTemplateService {

    Page<WorkflowStageTemplateDTO> getAllWorkflowStageTemplate(final Pageable pageable);

    Optional<WorkflowStageTemplateDTO> getWorkflowStageTemplateById(Long id);

    WorkflowStageTemplateDTO saveWorkflowStageTemplate(WorkflowStageTemplateDTO workflowStageTemplateDTO);

    WorkflowStageTemplateDTO updateWorkflowStageTemplate(Long id, WorkflowStageTemplateDTO workflowStageTemplateDTO);

    Boolean deleteWorkflowStageTemplateById(Long id);

    void reorderStages(Long workflowId, List<Long> stageIdsInOrder);

    WorkflowStageTemplateDTO assignGroupToStage(Long stageId, String groupName);
}
