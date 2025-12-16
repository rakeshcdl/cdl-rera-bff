package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowActionCriteria;
import com.cdl.escrow.workflow.dto.WorkflowActionDTO;
import com.cdl.escrow.workflow.mapper.WorkflowActionMapper;
import com.cdl.escrow.workflow.repository.WorkflowActionRepository;
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
public class WorkflowActionCriteriaService  extends BaseSpecificationBuilder<WorkflowAction> implements Serializable {
    private final WorkflowActionRepository workflowActionRepository;

    private final WorkflowActionMapper workflowActionMapper;

    public Page<WorkflowActionDTO> findByCriteria(WorkflowActionCriteria criteria, Pageable pageable) {
        Specification<WorkflowAction> specification = createSpecification(criteria);
        return workflowActionRepository.findAll(specification, pageable).map(workflowActionMapper::toDto);
    }

    public Specification<WorkflowAction> createSpecification(WorkflowActionCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "actionKey", criteria.getActionKey(), true);
                addStringFilter(cb, root, predicates, "actionName", criteria.getActionName(), true);
                addStringFilter(cb, root, predicates, "description", criteria.getDescription(), true);
                addStringFilter(cb, root, predicates, "moduleCode", criteria.getModuleCode(), true);
                addStringFilter(cb, root, predicates, "name", criteria.getName(),true);

                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());


            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
