package com.cdl.escrow.workflow.criteriaservice;


import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.entity.WorkflowRequestStageApproval;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowRequestStageApprovalCriteria;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageApprovalDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestStageApprovalMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageApprovalRepository;
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
public class WorkflowRequestStageApprovalCriteriaService extends BaseSpecificationBuilder<WorkflowRequestStageApproval> implements Serializable {
    private final WorkflowRequestStageApprovalRepository workflowRequestStageApprovalRepository;

    private final WorkflowRequestStageApprovalMapper workflowRequestStageApprovalMapper;

    public Page<WorkflowRequestStageApprovalDTO> findByCriteria(WorkflowRequestStageApprovalCriteria criteria, Pageable pageable) {
        Specification<WorkflowRequestStageApproval> specification = createSpecification(criteria);
        return workflowRequestStageApprovalRepository.findAll(specification, pageable).map(workflowRequestStageApprovalMapper::toDto);
    }

    public Specification<WorkflowRequestStageApproval> createSpecification(WorkflowRequestStageApprovalCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "approverUserId", criteria.getApproverUserId(), true);
                addStringFilter(cb, root, predicates, "approverUsername", criteria.getApproverUsername(), true);
                addStringFilter(cb, root, predicates, "approverGroup", criteria.getApproverGroup(), true);
                addStringFilter(cb, root, predicates, "remarks", criteria.getRemarks(), true);
                addZonedDateTimeFilter(cb, root, predicates, "decidedAt", criteria.getDecidedAt());

                // relationships

                if (criteria.getWorkflowRequestStageId() != null) {
                    Join<WorkflowRequestStage, WorkflowRequestStageApproval> join = root.join("workflowRequestStage", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getWorkflowRequestStageId());
                }

                if (criteria.getTaskStatusId() != null) {
                    Join<WorkflowRequestStageApproval, ApplicationSetting> join = root.join("taskStatus", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getTaskStatusId());
                }

            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
