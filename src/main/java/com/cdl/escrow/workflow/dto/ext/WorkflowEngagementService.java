package com.cdl.escrow.workflow.dto.ext;

import com.cdl.escrow.workflow.dto.MyEngagementDTO;
import com.cdl.escrow.workflow.enume.RoleView;
import com.cdl.escrow.workflow.repository.WorkflowRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WorkflowEngagementService {

    private final WorkflowRequestRepository requestRepo;

    public WorkflowEngagementService(WorkflowRequestRepository requestRepo) {
        this.requestRepo = requestRepo;
    }

    public Page<MyEngagementDTO> myEngagements(
            String userId,
            RoleView role,
            String moduleName,
            Pageable pageable
    ) {
        boolean makersOnly = (role == RoleView.MAKER);
        boolean actorsOnly = (role == RoleView.CHECKER || role == RoleView.APPROVER || role == RoleView.REVIEWER);
        boolean allRoles   = (role == RoleView.ALL || role == RoleView.ADMIN);

        return requestRepo.findMyEngagements(userId, makersOnly, actorsOnly, allRoles, moduleName, pageable);
    }
}
