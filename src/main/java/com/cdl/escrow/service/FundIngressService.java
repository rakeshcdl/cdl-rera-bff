package com.cdl.escrow.service;

import com.cdl.escrow.dto.FundIngressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FundIngressService {
    Page<FundIngressDTO> getAllFundIngress(final Pageable pageable);

    Optional<FundIngressDTO> getFundIngressById(Long id);

    FundIngressDTO saveFundIngress(FundIngressDTO fundIngressDTO);

    FundIngressDTO updateFundIngress(Long id, FundIngressDTO fundIngressDTO);

    Boolean deleteFundIngressById(Long id);

    boolean softFundIngressServiceById(Long id);
}
