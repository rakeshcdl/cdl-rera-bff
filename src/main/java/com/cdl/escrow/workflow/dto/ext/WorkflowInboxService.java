package com.cdl.escrow.workflow.dto.ext;

import com.cdl.escrow.workflow.enume.RoleView;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowInboxService {

    private final WorkflowRequestStageRepository stageRepo;

    public WorkflowInboxService(WorkflowRequestStageRepository stageRepo) {
        this.stageRepo = stageRepo;
    }

    public Page<AwaitingActionDTO> awaitingForRole(
            String moduleName,
            RoleView role,
            java.util.Collection<String> userAuthorities,
            Pageable pageable
    ) {
        var groups = RoleGroupResolver.resolveGroupsForRole(moduleName, role, userAuthorities);
        if (log.isDebugEnabled()) {
            log.debug("WorkflowInbox awaiting | module={}, role={}, authoritiesCount={}, resolvedGroups={}",
                    moduleName, role, userAuthorities == null ? 0 : userAuthorities.size(), groups);
        }
        if (groups.isEmpty()) return Page.empty(pageable);

        var activeStage = java.util.List.of(
                TaskStatusCodes.PENDING,
                TaskStatusCodes.IN_PROGRESS
        );
        var activeRequest = java.util.List.of(
                TaskStatusCodes.PENDING,
                TaskStatusCodes.IN_PROGRESS
        );

        return stageRepo.findAwaitingForGroups(groups, activeStage, activeRequest, moduleName, pageable);
    }
}
