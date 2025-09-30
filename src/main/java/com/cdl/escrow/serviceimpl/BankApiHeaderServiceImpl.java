
package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BankApiHeaderDTO;
import com.cdl.escrow.entity.BankApiHeader;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BankApiHeaderMapper;
import com.cdl.escrow.repository.BankApiHeaderRepository;
import com.cdl.escrow.service.BankApiHeaderService;
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
public class BankApiHeaderServiceImpl implements BankApiHeaderService {

    private final BankApiHeaderRepository repository;

    private final BankApiHeaderMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BankApiHeaderDTO> getAllBankApiHeader(Pageable pageable) {
        log.debug("Fetching all Bank API Header, page: {}", pageable.getPageNumber());
        Page<BankApiHeader> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankApiHeaderDTO> getBankApiHeaderById(Long id) {
        log.debug("Fetching Bank API Header with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public BankApiHeaderDTO saveBankApiHeader(BankApiHeaderDTO bankApiHeaderDTO) {
        log.info("Saving new Bank API Header");
        BankApiHeader entity = mapper.toEntity(bankApiHeaderDTO);
        BankApiHeader saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BankApiHeaderDTO updateBankApiHeader(Long id, BankApiHeaderDTO bankApiHeaderDTO) {
        log.info("Updating Bank API Header with ID: {}", id);

        BankApiHeader existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Bank API Header not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BankApiHeader toUpdate = mapper.toEntity(bankApiHeaderDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BankApiHeader updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteBankApiHeaderById(Long id) {
        log.info("Deleting Bank API Header with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Bank API Header not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBankApiHeaderById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
