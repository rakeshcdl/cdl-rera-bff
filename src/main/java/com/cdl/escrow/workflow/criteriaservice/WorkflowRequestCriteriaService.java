package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowRequestCriteria;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestRepository;
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
public class WorkflowRequestCriteriaService extends BaseSpecificationBuilder<WorkflowRequest> implements Serializable {

    private final transient WorkflowRequestRepository workflowRequestRepository;

    private final transient WorkflowRequestMapper workflowRequestMapper;

    public Page<WorkflowRequestDTO> findByCriteria(WorkflowRequestCriteria criteria, Pageable pageable) {
        Specification<WorkflowRequest> specification = createSpecification(criteria);
        return workflowRequestRepository.findAll(specification, pageable).map(workflowRequestMapper::toDto);
    }
    public Specification<WorkflowRequest> createSpecification(WorkflowRequestCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "referenceId", criteria.getReferenceId(), true);
                addStringFilter(cb, root, predicates, "referenceType", criteria.getReferenceType(), true);
                addStringFilter(cb, root, predicates, "moduleName", criteria.getModuleName(), true);
                addStringFilter(cb, root, predicates, "actionKey", criteria.getActionKey(), true);
                addDoubleFilter(cb, root, predicates, "amount", criteria.getAmount());
                addStringFilter(cb, root, predicates, "currency", criteria.getCurrency(), true);
                addIntegerFilter(cb, root, predicates, "currentStageOrder", criteria.getCurrentStageOrder());
                addStringFilter(cb, root, predicates, "createdBy", criteria.getCreatedBy(), true);
                addZonedDateTimeFilter(cb, root, predicates, "createdAt", criteria.getCreatedAt());
                addZonedDateTimeFilter(cb, root, predicates, "lastUpdatedAt", criteria.getLastUpdatedAt());
                addInstantFilter(cb, root, predicates, "version", criteria.getVersion());

                // relationships

                if (criteria.getTaskStatusId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "taskStatus", "id", criteria.getTaskStatusId());
                }

                if (criteria.getWorkflowDefinitionId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "workflowDefinition", "id", criteria.getWorkflowDefinitionId());
                }


            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
