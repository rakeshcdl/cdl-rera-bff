package com.cdl.escrow.service;

import com.cdl.escrow.dto.SecondaryBankAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SecondaryBankAccountService {
    Page<SecondaryBankAccountDTO> getAllSecondaryBankAccount(final Pageable pageable);

    Optional<SecondaryBankAccountDTO> getSecondaryBankAccountById(Long id);

    SecondaryBankAccountDTO saveSecondaryBankAccount(SecondaryBankAccountDTO secondaryBankAccountDTO);

    SecondaryBankAccountDTO updateSecondaryBankAccount(Long id, SecondaryBankAccountDTO secondaryBankAccountDTO);

    Boolean deleteSecondaryBankAccountById(Long id);

    boolean softSecondaryBankAccountServiceById(Long id);
}
