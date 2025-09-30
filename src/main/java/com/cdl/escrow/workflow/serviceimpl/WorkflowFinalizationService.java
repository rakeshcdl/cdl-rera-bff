package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import com.cdl.escrow.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkflowFinalizationService {

    private final BuildPartnerService buildPartnerService;

    private final RealEstateAssestService realEstateAssestService;

    private final CapitalPartnerService capitalPartnerService;

    private final FundEgressService fundEgressService;

    private final PendingFundIngressService pendingFundIngressService;

    private final SuretyBondService suretyBondService;

    public void finalizeWorkflow(WorkflowRequest request) {
        String moduleType = request.getModuleName();  // e.g. "PAYMENT", "BUILD_PARTNER", "BUILD_PARTNER_ASSETS"
        Long moduleId = Long.valueOf(request.getReferenceId());
       TaskStatus status = request.getTaskStatus();

        switch (moduleType) {
            case "BUILD_PARTNER" -> buildPartnerService.finalizeBuildPartner(moduleId, status);
            case "BUILD_PARTNER_ASSEST" -> realEstateAssestService.finalizeRealEstateAssest(moduleId, status);
            case "PAYMENTS" -> fundEgressService.finalizeFundEgress(moduleId, status);
            case "CAPITAL_PARTNER" -> capitalPartnerService.finalizeCapitalPartner(moduleId, status);
            case "TRANSACTIONS" -> pendingFundIngressService.finalizeDeposit(moduleId, status);
            case "SURETY_BOND" -> suretyBondService.finalizeSuretyBond(moduleId,status);
            default -> throw new IllegalArgumentException("Unsupported module type: " + moduleType);
        }
    }
}
