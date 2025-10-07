package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.workflow.dto.StageActionRequest;
import com.cdl.escrow.workflow.service.WorkflowExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workflow-execution")
@RequiredArgsConstructor
@Slf4j
public class WorkflowExecutionController {

    private final WorkflowExecutionService workflowExecutionService;

    @PostMapping("/{stageId}/decision")
    public ResponseEntity<Void> decideStage(
            @PathVariable Long stageId,
            @RequestBody StageActionRequest request) throws Exception {

        workflowExecutionService.executeStage(stageId,
                request.getUserId(),
                request.getDecision(),
                request.getRemarks());

        return ResponseEntity.ok().build();
    }
}
