/**
 * BankAccountService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.BankAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankAccountService {

    Page<BankAccountDTO> getAllBankAccount(final Pageable pageable);

    Optional<BankAccountDTO> getBankAccountById(Long id);

    BankAccountDTO saveBankAccount(BankAccountDTO bankAccountDTO);

    BankAccountDTO updateBankAccount(Long id, BankAccountDTO bankAccountDTO);

    Boolean deleteBankAccountById(Long id);
}
