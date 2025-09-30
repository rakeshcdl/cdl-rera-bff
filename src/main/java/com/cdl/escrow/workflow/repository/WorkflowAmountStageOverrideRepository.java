package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowAmountStageOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowAmountStageOverrideRepository extends JpaRepository<WorkflowAmountStageOverride,Long>, JpaSpecificationExecutor<WorkflowAmountStageOverride> {

    @Query("""
        SELECT o 
        FROM WorkflowAmountStageOverride o
        WHERE o.workflowAmountRule.id = :ruleId
          AND o.stageOrder = :stageOrder
    """)
    Optional<WorkflowAmountStageOverride> findByAmountRuleIdAndStageOrder(@Param("ruleId") Long ruleId,
                                                                          @Param("stageOrder") Integer stageOrder);


}

