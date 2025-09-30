package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerBankInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CapitalPartnerBankInfoService {
    Page<CapitalPartnerBankInfoDTO> getAllCapitalPartnerBankInfo(final Pageable pageable);

    Optional<CapitalPartnerBankInfoDTO> getCapitalPartnerBankInfoById(Long id);

    CapitalPartnerBankInfoDTO saveCapitalPartnerBankInfo(CapitalPartnerBankInfoDTO capitalPartnerBankInfoDTO);

    CapitalPartnerBankInfoDTO updateCapitalPartnerBankInfo(Long id, CapitalPartnerBankInfoDTO capitalPartnerBankInfoDTO);

    Boolean deleteCapitalPartnerBankInfoById(Long id);

    boolean softCapitalPartnerBankInfoServiceById(Long id);
}
