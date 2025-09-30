/**
 * BankApiHeaderService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.BankApiHeaderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankApiHeaderService {

    Page<BankApiHeaderDTO> getAllBankApiHeader(final Pageable pageable);

    Optional<BankApiHeaderDTO> getBankApiHeaderById(Long id);

    BankApiHeaderDTO saveBankApiHeader(BankApiHeaderDTO bankApiHeaderDTO);

    BankApiHeaderDTO updateBankApiHeader(Long id, BankApiHeaderDTO bankApiHeaderDTO);

    Boolean deleteBankApiHeaderById(Long id);

    boolean softBankApiHeaderById(Long id);
}
