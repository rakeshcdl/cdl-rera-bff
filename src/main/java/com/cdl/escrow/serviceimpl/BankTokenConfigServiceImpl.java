
package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BankTokenConfigDTO;
import com.cdl.escrow.entity.BankTokenConfig;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BankTokenConfigMapper;
import com.cdl.escrow.repository.BankTokenConfigRepository;
import com.cdl.escrow.service.BankTokenConfigService;
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
public class BankTokenConfigServiceImpl implements BankTokenConfigService {

    private final BankTokenConfigRepository repository;

    private final BankTokenConfigMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BankTokenConfigDTO> getAllBankTokenConfig(Pageable pageable) {
        log.debug("Fetching all Bank Token Config, page: {}", pageable.getPageNumber());
        Page<BankTokenConfig> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankTokenConfigDTO> getBankTokenConfigById(Long id) {
        log.debug("Fetching Bank Token Config with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public BankTokenConfigDTO saveBankTokenConfig(BankTokenConfigDTO bankTokenConfigDTO) {
        log.info("Saving new Bank Token Config");
        BankTokenConfig entity = mapper.toEntity(bankTokenConfigDTO);
        BankTokenConfig saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BankTokenConfigDTO updateBankTokenConfig(Long id, BankTokenConfigDTO bankTokenConfigDTO) {
        log.info("Updating Bank Token Config with ID: {}", id);

        BankTokenConfig existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Bank Config not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BankTokenConfig toUpdate = mapper.toEntity(bankTokenConfigDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BankTokenConfig updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteBankTokenConfigById(Long id) {
        log.info("Deleting Bank Token Config with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Bank Token Config not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBankTokenConfigById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
