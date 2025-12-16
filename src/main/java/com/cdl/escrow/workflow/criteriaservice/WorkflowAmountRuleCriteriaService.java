package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowAmountRuleCriteria;
import com.cdl.escrow.workflow.dto.WorkflowAmountRuleDTO;
import com.cdl.escrow.workflow.mapper.WorkflowAmountRuleMapper;
import com.cdl.escrow.workflow.repository.WorkflowAmountRuleRepository;
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
public class WorkflowAmountRuleCriteriaService  extends BaseSpecificationBuilder<WorkflowAmountRule> implements Serializable {
    private final WorkflowAmountRuleRepository workflowAmountRuleRepository;

    private final WorkflowAmountRuleMapper workflowAmountRuleMapper;

    public Page<WorkflowAmountRuleDTO> findByCriteria(WorkflowAmountRuleCriteria criteria, Pageable pageable) {
        Specification<WorkflowAmountRule> specification = createSpecification(criteria);
        return workflowAmountRuleRepository.findAll(specification, pageable).map(workflowAmountRuleMapper::toDto);
    }

    public Specification<WorkflowAmountRule> createSpecification(WorkflowAmountRuleCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "currency", criteria.getCurrency(), true);
                addDoubleFilter(cb, root, predicates, "minAmount", criteria.getMinAmount());
                addDoubleFilter(cb, root, predicates, "maxAmount", criteria.getMaxAmount());
                addIntegerFilter(cb, root, predicates, "priority", criteria.getPriority());
                addIntegerFilter(cb, root, predicates, "requiredMakers", criteria.getRequiredMakers());

                addIntegerFilter(cb, root, predicates, "requiredCheckers", criteria.getRequiredCheckers());
                addBooleanFilter(cb, root, predicates, "isActive", criteria.getIsActive());
                addLongFilter(cb, root, predicates, "workflowId", criteria.getWorkflowId());
                addStringFilter(cb, root, predicates, "amountRuleName", criteria.getAmountRuleName(), true);


                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());


                // relationships

                if (criteria.getWorkflowDefinitionId() != null) {
                    Join<WorkflowAmountRule, WorkflowDefinition> join = root.join("workflowDefinition", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getWorkflowDefinitionId());
                }



            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
