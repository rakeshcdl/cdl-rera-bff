package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowAmountRule;
import com.cdl.escrow.entity.WorkflowDefinition;
import com.cdl.escrow.entity.WorkflowStageTemplate;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowAmountRuleDTO;
import com.cdl.escrow.workflow.mapper.WorkflowAmountRuleMapper;
import com.cdl.escrow.workflow.repository.WorkflowAmountRuleRepository;
import com.cdl.escrow.workflow.service.WorkflowAmountRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowAmountRuleServiceImpl implements WorkflowAmountRuleService {

    private final WorkflowAmountRuleRepository repository;

    private final WorkflowAmountRuleMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkflowAmountRuleDTO> getAllWorkflowAmountRule(Pageable pageable) {
        log.debug("Fetching all workflow amount rule, page: {}", pageable.getPageNumber());
        Page<WorkflowAmountRule> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkflowAmountRuleDTO> getWorkflowAmountRuleById(Long id) {
        log.debug("Fetching workflow amount rule with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public WorkflowAmountRuleDTO saveWorkflowAmountRule(WorkflowAmountRuleDTO workflowAmountRuleDTO) {
        log.info("Saving new workflow amount rule");
        WorkflowAmountRule entity = mapper.toEntity(workflowAmountRuleDTO);
        WorkflowAmountRule saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public WorkflowAmountRuleDTO updateWorkflowAmountRule(Long id, WorkflowAmountRuleDTO workflowAmountRuleDTO) {
        log.info("Updating workflow amount rule with ID: {}", id);

        WorkflowAmountRule existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow amount rule not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowAmountRule toUpdate = mapper.toEntity(workflowAmountRuleDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowAmountRule updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteWorkflowAmountRuleById(Long id) {
        log.info("Deleting workflow amount rule with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow amount rule not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }



    // === Business Specific ===
    public WorkflowAmountRuleDTO resolveRule(Long workflowDefinitionId, BigDecimal amount) {
        return repository.findByWorkflowDefinitionId(workflowDefinitionId).stream()
                .filter(rule -> amount.compareTo(rule.getMinAmount()) >= 0 &&
                        amount.compareTo(rule.getMaxAmount()) <= 0)
                .findFirst()
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("No applicable rule for amount " + amount));
    }

    @Override
    public List<WorkflowStageTemplate> getStagesForAmount(WorkflowDefinition workflowDefinition, BigDecimal amount) {
        return List.of();
    }

}
