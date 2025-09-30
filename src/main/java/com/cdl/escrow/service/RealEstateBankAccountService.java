package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateBankAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateBankAccountService {
    Page<RealEstateBankAccountDTO> getAllRealEstateBankAccount(final Pageable pageable);

    Optional<RealEstateBankAccountDTO> getRealEstateBankAccountById(Long id);

    RealEstateBankAccountDTO saveRealEstateBankAccount(RealEstateBankAccountDTO realEstateBankAccountDTO);

    RealEstateBankAccountDTO updateRealEstateBankAccount(Long id, RealEstateBankAccountDTO realEstateBankAccountDTO);

    Boolean deleteRealEstateBankAccountById(Long id);

    boolean softRealEstateBankAccountServiceById(Long id);
}
