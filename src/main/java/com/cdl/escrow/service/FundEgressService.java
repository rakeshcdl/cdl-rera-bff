package com.cdl.escrow.service;

import com.cdl.escrow.dto.FundEgressDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FundEgressService {
    Page<FundEgressDTO> getAllFundEgress(final Pageable pageable);

    Optional<FundEgressDTO> getFundEgressById(Long id);

    FundEgressDTO saveFundEgress(FundEgressDTO fundEgressDTO);

    FundEgressDTO updateFundEgress(Long id, FundEgressDTO fundEgressDTO);

    Boolean deleteFundEgressById(Long id);


    void finalizeFundEgress(Long moduleId, TaskStatus status);

    boolean softFundEgressServiceById(Long id);
}
