package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.entity.WorkflowDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition,Long>, JpaSpecificationExecutor<WorkflowDefinition> {
    Page<WorkflowDefinition> findByModuleCodeAndActionCodeAndNameContainingIgnoreCase(String moduleCode, String actionCode, String keyword, Pageable pageable);

    Page<WorkflowDefinition> findByModuleCodeAndActionCode(String moduleCode, String actionCode, Pageable pageable);

    Page<WorkflowDefinition> findByModuleCode(String moduleCode, Pageable pageable);

    Page<WorkflowDefinition> findByActionCode(String actionCode, Pageable pageable);

    Page<WorkflowDefinition> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<WorkflowDefinition> findByApplicationModuleAndWorkflowActionAndIsActive(ApplicationModule module, WorkflowAction action, boolean active, Pageable pageable);

    Optional<WorkflowDefinition> findByModuleCodeAndActionCode(String moduleName, String actionKey);
}

