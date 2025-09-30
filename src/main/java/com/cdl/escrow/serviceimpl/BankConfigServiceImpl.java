
package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BankConfigDTO;
import com.cdl.escrow.entity.BankConfig;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BankConfigMapper;
import com.cdl.escrow.repository.BankConfigRepository;
import com.cdl.escrow.service.BankConfigService;
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
public class BankConfigServiceImpl implements BankConfigService {

    private final BankConfigRepository repository;

    private final BankConfigMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<BankConfigDTO> getAllBankConfig(Pageable pageable) {
        log.debug("Fetching all Bank Config, page: {}", pageable.getPageNumber());
        Page<BankConfig> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankConfigDTO> getBankConfigById(Long id) {
        log.debug("Fetching Bank Config with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public BankConfigDTO saveBankConfig(BankConfigDTO bankConfigDTO) {
        log.info("Saving new Bank Config");
        BankConfig entity = mapper.toEntity(bankConfigDTO);
        BankConfig saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BankConfigDTO updateBankConfig(Long id, BankConfigDTO bankConfigDTO) {
        log.info("Updating Bank Config with ID: {}", id);

        BankConfig existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Bank Config not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BankConfig toUpdate = mapper.toEntity(bankConfigDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BankConfig updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteBankConfigById(Long id) {
        log.info("Deleting Bank Config with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Bank Config not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBankConfigById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
