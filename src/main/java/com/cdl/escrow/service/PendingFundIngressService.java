package com.cdl.escrow.service;

import com.cdl.escrow.dto.PendingFundIngressDTO;
import com.cdl.escrow.dto.PendingFundIngressExtDTO;
import com.cdl.escrow.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PendingFundIngressService {

    Page<PendingFundIngressDTO> getAllPendingFundIngress(final Pageable pageable);

    Optional<PendingFundIngressDTO> getPendingFundIngressById(Long id);

    PendingFundIngressDTO savePendingFundIngress(PendingFundIngressDTO pendingFundIngressDTO);

    PendingFundIngressDTO updatePendingFundIngress(Long id, PendingFundIngressDTO pendingFundIngressDTO);

    Boolean deletePendingFundIngressById(Long id);

    boolean softPendingFundIngressServiceById(Long id);

    void finalizeDeposit(Long moduleId, TaskStatus status);

    PendingFundIngressExtDTO updateSplitData(PendingFundIngressExtDTO pendingFundIngressExtDTO);
}
