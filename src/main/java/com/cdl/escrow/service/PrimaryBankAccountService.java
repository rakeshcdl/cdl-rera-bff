package com.cdl.escrow.service;

import com.cdl.escrow.dto.PrimaryBankAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PrimaryBankAccountService {
    Page<PrimaryBankAccountDTO> getAllPrimaryBankAccount(final Pageable pageable);

    Optional<PrimaryBankAccountDTO> getPrimaryBankAccountById(Long id);

    PrimaryBankAccountDTO savePrimaryBankAccount(PrimaryBankAccountDTO primaryBankAccountDTO);

    PrimaryBankAccountDTO updatePrimaryBankAccount(Long id, PrimaryBankAccountDTO primaryBankAccountDTO);

    Boolean deletePrimaryBankAccountById(Long id);

    boolean softPrimaryBankAccountServiceById(Long id);
}
