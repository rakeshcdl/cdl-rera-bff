package com.cdl.escrow.workflow.criteriaservice;



import com.cdl.escrow.entity.WorkflowRequestLog;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowRequestLogCriteria;
import com.cdl.escrow.workflow.dto.WorkflowRequestLogDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestLogMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestLogRepository;
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
public class WorkflowRequestLogCriteriaService extends BaseSpecificationBuilder<WorkflowRequestLog> implements Serializable {

    private final transient WorkflowRequestLogRepository workflowRequestLogRepository;

    private final transient WorkflowRequestLogMapper workflowRequestLogMapper;

    public Page<WorkflowRequestLogDTO> findByCriteria(WorkflowRequestLogCriteria criteria, Pageable pageable) {
        Specification<WorkflowRequestLog> specification = createSpecification(criteria);
        return workflowRequestLogRepository.findAll(specification, pageable).map(workflowRequestLogMapper::toDto);
    }
    public Specification<WorkflowRequestLog> createSpecification(WorkflowRequestLogCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "eventType", criteria.getEventType(), true);
                addStringFilter(cb, root, predicates, "eventByUser", criteria.getEventByUser(), true);
                addStringFilter(cb, root, predicates, "eventByGroup", criteria.getEventByGroup(), true);
                addZonedDateTimeFilter(cb, root, predicates, "eventAt", criteria.getEventAt());

                // relationships
                if (criteria.getWorkflowRequestId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "workflowRequest", "id", criteria.getWorkflowRequestId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
