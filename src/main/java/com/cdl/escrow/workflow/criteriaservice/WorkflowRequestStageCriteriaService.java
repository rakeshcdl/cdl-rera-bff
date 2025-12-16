package com.cdl.escrow.workflow.criteriaservice;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.workflow.criteria.WorkflowRequestStageCriteria;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestStageMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
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
public class WorkflowRequestStageCriteriaService  extends BaseSpecificationBuilder<WorkflowRequestStage> implements Serializable {
    private final WorkflowRequestStageRepository workflowRequestStageRepository;

    private final WorkflowRequestStageMapper workflowRequestStageMapper;

    public Page<WorkflowRequestStageDTO> findByCriteria(WorkflowRequestStageCriteria criteria, Pageable pageable) {
        Specification< WorkflowRequestStage> specification = createSpecification(criteria);
        return workflowRequestStageRepository.findAll(specification, pageable).map(workflowRequestStageMapper::toDto);
    }

    public Specification< WorkflowRequestStage> createSpecification(WorkflowRequestStageCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addIntegerFilter(cb, root, predicates, "stageOrder", criteria.getStageOrder());
                addStringFilter(cb, root, predicates, "stageKey", criteria.getStageKey(), true);
                addStringFilter(cb, root, predicates, "keycloakGroup", criteria.getKeycloakGroup(), true);
                addIntegerFilter(cb, root, predicates, "requiredApprovals", criteria.getRequiredApprovals());
                addIntegerFilter(cb, root, predicates, "approvalsObtained", criteria.getApprovalsObtained());
                addZonedDateTimeFilter(cb, root, predicates, "startedAt", criteria.getStartedAt());
                addZonedDateTimeFilter(cb, root, predicates, "completedAt", criteria.getCompletedAt());
                addLongFilter(cb, root, predicates, "version", criteria.getVersion());


                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

                // relationships

                if (criteria.getWorkflowRequestId() != null) {
                    Join<WorkflowRequestStage, WorkflowRequest> join = root.join("workflowRequest", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getWorkflowRequestId());
                }



            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




}
