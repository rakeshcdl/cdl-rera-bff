package com.cdl.escrow.workflow.repository;

import com.cdl.escrow.entity.WorkflowAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowActionRepository extends JpaRepository<WorkflowAction,Long>, JpaSpecificationExecutor<WorkflowAction> {


        Page<WorkflowAction> findByModuleCode(String moduleCode, Pageable pageable);

        Page<WorkflowAction> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

        Page<WorkflowAction> findByModuleCodeAndActionNameContainingIgnoreCase(
                String moduleCode, String keyword, Pageable pageable);

}

