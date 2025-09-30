package com.cdl.escrow.workflow.dto.ext;

import com.cdl.escrow.workflow.dto.MyEngagementDTO;
import com.cdl.escrow.workflow.enume.RoleView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflow-engagements")
@RequiredArgsConstructor
@Slf4j
public class WorkflowEngagementController {

    private final WorkflowEngagementService engagementService;


    // Example: GET /api/v1/workflow/engagements?userId=jane&role=ALL&module=payments
    @GetMapping
    public org.springframework.http.ResponseEntity<org.springframework.data.domain.Page<MyEngagementDTO>> myEngagements(
            @RequestParam String userId,
            @RequestParam(defaultValue = "ALL") RoleView role,
            @RequestParam(required = false) String module,
            @ParameterObject Pageable pageable) {

        return org.springframework.http.ResponseEntity.ok(
                engagementService.myEngagements(userId, role, module, pageable)
        );
    }
}
