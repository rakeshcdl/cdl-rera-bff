package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowStageTemplateCriteria;
import com.cdl.escrow.workflow.dto.WorkflowStageTemplateDTO;
import com.cdl.escrow.workflow.mapper.WorkflowStageTemplateMapper;
import com.cdl.escrow.workflow.repository.WorkflowStageTemplateRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowStageTemplateCriteriaService  extends BaseSpecificationBuilder<WorkflowStageTemplate> implements Serializable {
    private final WorkflowStageTemplateRepository workflowStageTemplateRepository;

    private final WorkflowStageTemplateMapper workflowStageTemplateMapper;

    public Page<WorkflowStageTemplateDTO> findByCriteria(WorkflowStageTemplateCriteria criteria, Pageable pageable) {
        Specification<WorkflowStageTemplate> specification = createSpecification(criteria);
        return workflowStageTemplateRepository.findAll(specification, pageable).map(workflowStageTemplateMapper::toDto);
    }

    public Specification<WorkflowStageTemplate> createSpecification(WorkflowStageTemplateCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addIntegerFilter(cb, root, predicates, "stageOrder", criteria.getStageOrder());
                addStringFilter(cb, root, predicates, "stageKey", criteria.getStageKey(), true);
                addStringFilter(cb, root, predicates, "keycloakGroup", criteria.getKeycloakGroup(), true);
                addIntegerFilter(cb, root, predicates, "requiredApprovals", criteria.getRequiredApprovals());
                addStringFilter(cb, root, predicates, "name", criteria.getName(),true);

                addStringFilter(cb, root, predicates, "description", criteria.getDescription(), true);
                addIntegerFilter(cb, root, predicates, "slaHours", criteria.getSlaHours());

                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

                // relationships

                if (criteria.getWorkflowDefinitionId() != null) {
                    Join<WorkflowStageTemplate, WorkflowDefinition> join = root.join("workflowDefinition", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getWorkflowDefinitionId());
                }



            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
