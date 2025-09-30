package com.cdl.escrow.service;

import com.cdl.escrow.dto.FinancialInstitutionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FinancialInstitutionService {
    Page<FinancialInstitutionDTO> getAllFinancialInstitution(final Pageable pageable);

    Optional<FinancialInstitutionDTO> getFinancialInstitutionById(Long id);

    FinancialInstitutionDTO saveFinancialInstitution(FinancialInstitutionDTO financialInstitutionDTO);

    FinancialInstitutionDTO updateFinancialInstitution(Long id, FinancialInstitutionDTO financialInstitutionDTO);

    Boolean deleteFinancialInstitutionById(Long id);

    boolean softFinancialInstitutionServiceById(Long id);
}
