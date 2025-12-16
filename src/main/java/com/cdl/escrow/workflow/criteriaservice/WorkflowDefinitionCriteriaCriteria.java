package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowDefinitionCriteria;
import com.cdl.escrow.workflow.dto.WorkflowDefinitionDTO;
import com.cdl.escrow.workflow.mapper.WorkflowDefinitionMapper;
import com.cdl.escrow.workflow.repository.WorkflowDefinitionRepository;
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
public class WorkflowDefinitionCriteriaCriteria  extends BaseSpecificationBuilder<WorkflowDefinition> implements Serializable {
    private final WorkflowDefinitionRepository workflowDefinitionRepository;

    private final WorkflowDefinitionMapper workflowDefinitionMapper;

    public Page<WorkflowDefinitionDTO> findByCriteria(WorkflowDefinitionCriteria criteria, Pageable pageable) {
        Specification<WorkflowDefinition> specification = createSpecification(criteria);
        return workflowDefinitionRepository.findAll(specification, pageable).map(workflowDefinitionMapper::toDto);
    }

    public Specification<WorkflowDefinition> createSpecification(WorkflowDefinitionCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "name", criteria.getName(), true);
                addIntegerFilter(cb, root, predicates, "version", criteria.getVersion());
                addBooleanFilter(cb, root, predicates, "isActive", criteria.getIsActive());
                addStringFilter(cb, root, predicates, "createdBy", criteria.getCreatedBy(), true);
                addBooleanFilter(cb, root, predicates, "amountBased", criteria.getAmountBased());

                addStringFilter(cb, root, predicates, "moduleCode", criteria.getModuleCode(), true);
                addStringFilter(cb, root, predicates, "actionCode", criteria.getActionCode(), true);
                addZonedDateTimeFilter(cb, root, predicates, "createdAt", criteria.getCreatedAt());

                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());


                // relationships

                if (criteria.getApplicationModuleId() != null) {
                    Join<WorkflowDefinition, ApplicationModule> join = root.join("applicationModule", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getApplicationModuleId());
                }

                if (criteria.getWorkflowActionId() != null) {
                    Join<WorkflowDefinition, WorkflowAction> join = root.join("workflowAction", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getWorkflowActionId());
                }

            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
