package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowStageTemplate;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowStageTemplateDTO;
import com.cdl.escrow.workflow.mapper.WorkflowStageTemplateMapper;
import com.cdl.escrow.workflow.repository.WorkflowStageTemplateRepository;
import com.cdl.escrow.workflow.service.WorkflowStageTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowStageTemplateServiceImpl implements WorkflowStageTemplateService {


    private final WorkflowStageTemplateRepository repository;

    private final WorkflowStageTemplateMapper mapper;


    @Override
    public Page<WorkflowStageTemplateDTO> getAllWorkflowStageTemplate(Pageable pageable) {
        log.debug("Fetching all workflow stage template , page: {}", pageable.getPageNumber());
        Page<WorkflowStageTemplate> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Optional<WorkflowStageTemplateDTO> getWorkflowStageTemplateById(Long id) {
        log.debug("Fetching workflow stage template with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public WorkflowStageTemplateDTO saveWorkflowStageTemplate(WorkflowStageTemplateDTO workflowStageTemplateDTO) {
        log.info("Saving new workflow stage template");
        WorkflowStageTemplate entity = mapper.toEntity(workflowStageTemplateDTO);
        WorkflowStageTemplate saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public WorkflowStageTemplateDTO updateWorkflowStageTemplate(Long id, WorkflowStageTemplateDTO workflowStageTemplateDTO) {
        log.info("Updating workflow stage template with ID: {}", id);

        WorkflowStageTemplate existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow stage template not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowStageTemplate toUpdate = mapper.toEntity(workflowStageTemplateDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowStageTemplate updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    public Boolean deleteWorkflowStageTemplateById(Long id) {
        log.info("Deleting workflow stage template with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow stage template not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    // === Business Specific ===
    public void reorderStages(Long workflowDefinitionId, List<Long> stageIdsInOrder) {
        List<WorkflowStageTemplate> stages = repository.findByWorkflowDefinitionIdOrderByStageOrder(workflowDefinitionId);
        if (stages.size() != stageIdsInOrder.size()) {
            throw new IllegalArgumentException("Stage count mismatch during reordering");
        }

        for (int i = 0; i < stageIdsInOrder.size(); i++) {
            WorkflowStageTemplate stage = repository.findById(stageIdsInOrder.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid stage id"));
            stage.setStageOrder(i + 1);
            repository.save(stage);
        }
    }

    public WorkflowStageTemplateDTO assignGroupToStage(Long stageId, String groupName) {
        WorkflowStageTemplate stage = repository.findById(stageId)
                .orElseThrow(() -> new IllegalArgumentException("Stage not found"));

        stage.setKeycloakGroup(groupName);
        return mapper.toDto(repository.save(stage));
    }
}
