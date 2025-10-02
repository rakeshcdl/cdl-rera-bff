package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.entity.WorkflowDefinition;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WorkflowDefinitionRepository
        extends JpaRepository<WorkflowDefinition, Long>, JpaSpecificationExecutor<WorkflowDefinition> {

    // ---------- Basic filters (paged) ----------
    Page<WorkflowDefinition> findByModuleCodeAndActionCode(String moduleCode, String actionCode, Pageable pageable);

    Page<WorkflowDefinition> findByModuleCode(String moduleCode, Pageable pageable);

    Page<WorkflowDefinition> findByActionCode(String actionCode, Pageable pageable);

    Page<WorkflowDefinition> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<WorkflowDefinition> findByApplicationModuleAndWorkflowActionAndIsActive(
            ApplicationModule applicationModule,
            WorkflowAction workflowAction,
            boolean isActive,
            Pageable pageable
    );

    // Add name filter together with module & action
    Page<WorkflowDefinition> findByModuleCodeAndActionCodeAndNameContainingIgnoreCase(
            String moduleCode,
            String actionCode,
            String keyword,
            Pageable pageable
    );

    // ---------- Single-result lookups ----------
    // Active definition for a module+action
    Optional<WorkflowDefinition> findByModuleCodeAndActionCodeAndIsActiveTrue(String moduleCode, String actionCode);

    // Any (latest by your own logic) definition for module+action
    Optional<WorkflowDefinition> findByModuleCodeAndActionCode(String moduleCode, String actionCode);

    // ---------- Fetch graphs (stages + rules) ----------
    // Load stages and rules in one shot (no N+1)
    @NotNull
    @EntityGraph(attributePaths = { "workflowStageTemplates", "workflowAmountRules" })
    Optional<WorkflowDefinition> findById(@NotNull Long id);

    // If you prefer a separate name:
    @EntityGraph(attributePaths = { "workflowStageTemplates", "workflowAmountRules" })
    @Query("select w from WorkflowDefinition w where w.id = :id")
    Optional<WorkflowDefinition> findByIdWithStagesAndRules(@Param("id") Long id);
}


