package com.cdl.escrow.service;

import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProcessedFundIngressService {

    Page<ProcessedFundIngressDTO> getAllProcessedFundIngress(final Pageable pageable);

    Optional<ProcessedFundIngressDTO> getProcessedFundIngressById(Long id);

    ProcessedFundIngressDTO saveProcessedFundIngress(ProcessedFundIngressDTO processedFundIngressDTO);

    ProcessedFundIngressDTO updateProcessedFundIngress(Long id, ProcessedFundIngressDTO processedFundIngressDTO);

    Boolean deleteProcessedFundIngressById(Long id);

    boolean softProcessedFundIngressServiceById(Long id);
}
