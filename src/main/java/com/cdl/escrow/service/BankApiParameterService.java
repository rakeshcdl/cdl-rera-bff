/**
 * BankApiParameterService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.BankApiParameterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankApiParameterService {

    Page<BankApiParameterDTO> getAllBankApiParameter(final Pageable pageable);

    Optional<BankApiParameterDTO> getBankApiParameterById(Long id);

    BankApiParameterDTO saveBankApiParameter(BankApiParameterDTO bankApiParameterDTO);

    BankApiParameterDTO updateBankApiParameter(Long id, BankApiParameterDTO bankApiParameterDTO);

    Boolean deleteBankApiParameterById(Long id);

    boolean softBankApiParameterById(Long id);
}
