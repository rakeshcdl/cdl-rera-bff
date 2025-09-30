package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowAmountRuleDTO;
import com.cdl.escrow.workflow.dto.WorkflowDefinitionDTO;
import com.cdl.escrow.workflow.dto.WorkflowStageTemplateDTO;
import com.cdl.escrow.workflow.mapper.WorkflowDefinitionMapper;
import com.cdl.escrow.workflow.repository.WorkflowAmountRuleRepository;
import com.cdl.escrow.workflow.repository.WorkflowDefinitionRepository;
import com.cdl.escrow.workflow.repository.WorkflowStageTemplateRepository;
import com.cdl.escrow.workflow.service.WorkflowDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    private final WorkflowDefinitionRepository repository;

    private final WorkflowDefinitionMapper mapper;

    private final WorkflowStageTemplateRepository stageRepository;

    private final WorkflowAmountRuleRepository ruleRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkflowDefinitionDTO> getAllWorkflowDefinition(Pageable pageable) {
        log.debug("Fetching all workflow definition, page: {}", pageable.getPageNumber());
        Page<WorkflowDefinition> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkflowDefinitionDTO> getWorkflowDefinitionById(Long id) {
        log.debug("Fetching workflow definition with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public WorkflowDefinitionDTO saveWorkflowDefinition(WorkflowDefinitionDTO workflowDefinitionDTO) {
        log.info("Saving new workflow definition");
        WorkflowDefinition entity = mapper.toEntity(workflowDefinitionDTO);
        WorkflowDefinition saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public WorkflowDefinitionDTO updateWorkflowDefinition(Long id, WorkflowDefinitionDTO workflowDefinitionDTO) {
        log.info("Updating workflow definition with ID: {}", id);

        WorkflowDefinition existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow definition not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowDefinition toUpdate = mapper.toEntity(workflowDefinitionDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowDefinition updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteWorkflowDefinitionById(Long id) {
        log.info("Deleting workflow definition with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow definition not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    // business logic

    public Page<WorkflowDefinitionDTO> search(String moduleCode, String actionCode, String keyword, Pageable pageable) {
        Page<WorkflowDefinition> result;

        if (moduleCode != null && actionCode != null && keyword != null) {
            result = repository.findByModuleCodeAndActionCodeAndNameContainingIgnoreCase(moduleCode, actionCode, keyword, pageable);
        } else if (moduleCode != null && actionCode != null) {
            result = repository.findByModuleCodeAndActionCode(moduleCode, actionCode, pageable);
        } else if (moduleCode != null) {
            result = repository.findByModuleCode(moduleCode, pageable);
        } else if (actionCode != null) {
            result = repository.findByActionCode(actionCode, pageable);
        } else if (keyword != null) {
            result = repository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            result = repository.findAll(pageable);
        }

        return result.map(mapper::toDto);
    }

    public WorkflowDefinitionDTO create(WorkflowDefinitionDTO dto) {
        // validate uniqueness of stage order
        validateStages(dto.getStageTemplates());

        // validate amount rule ranges (if applicable)
        if (dto.isAmountBased()) {
            validateAmountRules(dto.getAmountRules());
        }

        WorkflowDefinition workflowDefinition = mapper.toEntity(dto);
        workflowDefinition.setCreatedAt(ZonedDateTime.now());
        workflowDefinition.setVersion(1);
        workflowDefinition.setIsActive(true);

        workflowDefinition = repository.save(workflowDefinition);

        // Save stages
        if (dto.getStageTemplates() != null) {
            for (WorkflowStageTemplateDTO stageDto : dto.getStageTemplates()) {
                WorkflowStageTemplate workflowStageTemplate = new WorkflowStageTemplate();
                workflowStageTemplate.setName(stageDto.getName());
                workflowStageTemplate.setStageOrder(stageDto.getStageOrder());
                workflowStageTemplate.setRequiredApprovals(stageDto.getRequiredApprovals());
                workflowStageTemplate.setKeycloakGroup(stageDto.getKeycloakGroup());
                workflowStageTemplate.setWorkflowDefinition(workflowDefinition);
                stageRepository.save(workflowStageTemplate);
            }
        }

        // Save amount rules
        if (dto.isAmountBased() && dto.getAmountRules() != null) {
            for (WorkflowAmountRuleDTO ruleDto : dto.getAmountRules()) {
                WorkflowAmountRule workflowAmountRule = new WorkflowAmountRule();
                workflowAmountRule.setMinAmount(BigDecimal.valueOf(ruleDto.getMinAmount()));
                workflowAmountRule.setMaxAmount(BigDecimal.valueOf(ruleDto.getMaxAmount()));
                workflowAmountRule.setRequiredMakers(ruleDto.getRequiredMakers());
                workflowAmountRule.setRequiredCheckers(ruleDto.getRequiredCheckers());
                workflowAmountRule.setCurrency(ruleDto.getCurrency());
                workflowAmountRule.setPriority(ruleDto.getPriority());
                workflowAmountRule.setStageOverrides(ruleDto.getWorkflowAmountStageOverrideDTOS());
                workflowAmountRule.setWorkflowDefinition(workflowDefinition);
                ruleRepository.save(workflowAmountRule);
            }
        }

        return mapper.toDto(workflowDefinition);
    }

    public WorkflowDefinitionDTO update(Long id, WorkflowDefinitionDTO dto) {
        WorkflowDefinition existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Definition not found"));

        // Deactivate old
        existing.setIsActive(false);
        repository.save(existing);

        // Create new version
        dto.setVersion(existing.getVersion() + 1);
        dto.setCreatedBy(dto.getCreatedBy());
        return create(dto);
    }

    public Page<WorkflowDefinitionDTO> advanceSearch(ApplicationModule module, WorkflowAction action,
                                                     boolean active, Pageable pageable) {
        return repository.findByApplicationModuleAndWorkflowActionAndIsActive(module, action, active, pageable)
                .map(mapper::toDto);
    }

    private void validateStages(List<WorkflowStageTemplateDTO> stages) {
        if (stages == null) return;
        Set<Integer> orders = new HashSet<>();
        for (WorkflowStageTemplateDTO stage : stages) {
            if (!orders.add(stage.getStageOrder())) {
                throw new IllegalArgumentException("Duplicate stage order: " + stage.getStageOrder());
            }
        }
    }

    private void validateAmountRules(List<WorkflowAmountRuleDTO> rules) {
        if (rules == null) return;
        rules.sort(Comparator.comparing(WorkflowAmountRuleDTO::getMinAmount));
        for (int i = 0; i < rules.size() - 1; i++) {
            if (rules.get(i).getMaxAmount().compareTo(rules.get(i + 1).getMinAmount()) >= 0) {
                throw new IllegalArgumentException("Overlapping amount ranges detected");
            }
        }
    }
}
