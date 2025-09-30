package com.cdl.escrow.dto;

import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingFundIngressExtDTO extends PendingFundIngressDTO{

    private Set<SplitDataDTO> splitData = new HashSet<>();

    private WorkflowRequestDTO workflowRequestDTO = new WorkflowRequestDTO();
}
