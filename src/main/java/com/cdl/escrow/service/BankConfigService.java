/**
 * BankConfigService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.BankConfigDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankConfigService {

    Page<BankConfigDTO> getAllBankConfig(final Pageable pageable);

    Optional<BankConfigDTO> getBankConfigById(Long id);

    BankConfigDTO saveBankConfig(BankConfigDTO bankConfigDTO);

    BankConfigDTO updateBankConfig(Long id, BankConfigDTO bankConfigDTO);

    Boolean deleteBankConfigById(Long id);

    boolean softBankConfigById(Long id);
}
