package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.FinancialInstitutionDTO;
import com.cdl.escrow.entity.FinancialInstitution;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.FinancialInstitutionMapper;
import com.cdl.escrow.repository.FinancialInstitutionRepository;
import com.cdl.escrow.service.FinancialInstitutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinancialInstitutionServiceImpl implements FinancialInstitutionService {

   private final FinancialInstitutionRepository repository;

    private final FinancialInstitutionMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<FinancialInstitutionDTO> getAllFinancialInstitution(Pageable pageable) {
        log.debug("Fetching all financial institution , page: {}", pageable.getPageNumber());
        Page<FinancialInstitution> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FinancialInstitutionDTO> getFinancialInstitutionById(Long id) {
        log.debug("Fetching financial institution with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public FinancialInstitutionDTO saveFinancialInstitution(FinancialInstitutionDTO financialInstitutionDTO) {
        log.info("Saving new financial institution");
        FinancialInstitution entity = mapper.toEntity(financialInstitutionDTO);
        FinancialInstitution saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public FinancialInstitutionDTO updateFinancialInstitution(Long id, FinancialInstitutionDTO financialInstitutionDTO) {
        log.info("Updating financial institution with ID: {}", id);

        FinancialInstitution existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Financial institution not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        FinancialInstitution toUpdate = mapper.toEntity(financialInstitutionDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        FinancialInstitution updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteFinancialInstitutionById(Long id) {
        log.info("Deleting financial institution  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Financial institution not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softFinancialInstitutionServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
