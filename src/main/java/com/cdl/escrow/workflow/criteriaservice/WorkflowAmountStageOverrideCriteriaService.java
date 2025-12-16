package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowAmountStageOverrideCriteria;
import com.cdl.escrow.workflow.dto.WorkflowAmountStageOverrideDTO;
import com.cdl.escrow.workflow.mapper.WorkflowAmountStageOverrideMapper;
import com.cdl.escrow.workflow.repository.WorkflowAmountStageOverrideRepository;
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
public class WorkflowAmountStageOverrideCriteriaService  extends BaseSpecificationBuilder<WorkflowAmountStageOverride> implements Serializable {
    private final WorkflowAmountStageOverrideRepository workflowAmountStageOverrideRepository;

    private final WorkflowAmountStageOverrideMapper workflowAmountStageOverrideMapper;

    public Page<WorkflowAmountStageOverrideDTO> findByCriteria(WorkflowAmountStageOverrideCriteria criteria, Pageable pageable) {
        Specification<WorkflowAmountStageOverride> specification = createSpecification(criteria);
        return workflowAmountStageOverrideRepository.findAll(specification, pageable).map(workflowAmountStageOverrideMapper::toDto);
    }

    public Specification<WorkflowAmountStageOverride> createSpecification(WorkflowAmountStageOverrideCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addIntegerFilter(cb, root, predicates, "stageOrder", criteria.getStageOrder());
                addStringFilter(cb, root, predicates, "stageKey", criteria.getStageKey(), true);
                addIntegerFilter(cb, root, predicates, "requiredApprovals", criteria.getRequiredApprovals());
                addStringFilter(cb, root, predicates, "keycloakGroup", criteria.getKeycloakGroup(), true);

                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());


                // relationships

                if (criteria.getWorkflowAmountRuleId() != null) {
                    Join<WorkflowAmountStageOverride, WorkflowAmountRule> join = root.join("workflowAmountRule", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getWorkflowAmountRuleId());
                }



            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
