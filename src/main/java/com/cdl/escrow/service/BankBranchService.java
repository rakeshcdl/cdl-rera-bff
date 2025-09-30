package com.cdl.escrow.service;

import com.cdl.escrow.dto.BankBranchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankBranchService {
    Page<BankBranchDTO> getAllBankBranch(final Pageable pageable);

    Optional<BankBranchDTO> getBankBranchById(Long id);

    BankBranchDTO saveBankBranch(BankBranchDTO bankBranchDTO);

    BankBranchDTO updateBankBranch(Long id, BankBranchDTO bankBranchDTO);

    Boolean deleteBankBranchById(Long id);

    boolean softBankBranchById(Long id);
}
