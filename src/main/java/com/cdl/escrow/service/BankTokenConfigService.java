/**
 * BankTokenConfigService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.BankTokenConfigDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankTokenConfigService {

    Page<BankTokenConfigDTO> getAllBankTokenConfig(final Pageable pageable);

    Optional<BankTokenConfigDTO> getBankTokenConfigById(Long id);

    BankTokenConfigDTO saveBankTokenConfig(BankTokenConfigDTO bankTokenConfigDTO);

    BankTokenConfigDTO updateBankTokenConfig(Long id, BankTokenConfigDTO bankTokenConfigDTO);

    Boolean deleteBankTokenConfigById(Long id);

    boolean softBankTokenConfigById(Long id);
}
