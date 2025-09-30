
package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BankApiParameterDTO;
import com.cdl.escrow.entity.BankApiParameter;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BankApiParameterMapper;
import com.cdl.escrow.repository.BankApiParameterRepository;
import com.cdl.escrow.service.BankApiParameterService;
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
public class BankApiParameterServiceImpl implements BankApiParameterService {

    private final BankApiParameterRepository repository;

    private final BankApiParameterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BankApiParameterDTO> getAllBankApiParameter(Pageable pageable) {
        log.debug("Fetching all Bank API Parameter, page: {}", pageable.getPageNumber());
        Page<BankApiParameter> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankApiParameterDTO> getBankApiParameterById(Long id) {
        log.debug("Fetching Bank API Parameter with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public BankApiParameterDTO saveBankApiParameter(BankApiParameterDTO bankApiParameterDTO) {
        log.info("Saving new Bank API Parameter");
        BankApiParameter entity = mapper.toEntity(bankApiParameterDTO);
        BankApiParameter saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BankApiParameterDTO updateBankApiParameter(Long id, BankApiParameterDTO bankApiParameterDTO) {
        log.info("Updating Bank API Parameter with ID: {}", id);

        BankApiParameter existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Bank API Parameter not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BankApiParameter toUpdate = mapper.toEntity(bankApiParameterDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BankApiParameter updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteBankApiParameterById(Long id) {
        log.info("Deleting Bank API Parameter with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Bank API Parameter not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBankApiParameterById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
