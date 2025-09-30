package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowAmountRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowAmountRuleRepository extends JpaRepository<WorkflowAmountRule,Long>, JpaSpecificationExecutor<WorkflowAmountRule> {
    List<WorkflowAmountRule> findByWorkflowDefinitionId(Long workflowDefinitionId);

   Optional<WorkflowAmountRule> findFirstByWorkflowIdAndMinAmountLessThanEqualAndMaxAmountGreaterThanEqual(Long workflowId, BigDecimal amount, BigDecimal amount1);

    @Query("""
        SELECT r 
        FROM WorkflowAmountRule r
        WHERE r.workflowDefinition.id = :definitionId
          AND :amount BETWEEN r.minAmount AND r.maxAmount
    """)
    Optional<WorkflowAmountRule> findApplicableRule(@Param("definitionId") Long definitionId,
                                                    @Param("amount") BigDecimal amount);
}
