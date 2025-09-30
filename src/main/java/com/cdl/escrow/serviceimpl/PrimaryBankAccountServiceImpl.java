package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.PrimaryBankAccountDTO;
import com.cdl.escrow.entity.PrimaryBankAccount;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.PrimaryBankAccountMapper;
import com.cdl.escrow.repository.PrimaryBankAccountRepository;
import com.cdl.escrow.service.PrimaryBankAccountService;
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
public class PrimaryBankAccountServiceImpl implements PrimaryBankAccountService {
   private final PrimaryBankAccountRepository repository;

   private final PrimaryBankAccountMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<PrimaryBankAccountDTO> getAllPrimaryBankAccount(Pageable pageable) {
        log.debug("Fetching all Primary Bank Account , page: {}", pageable.getPageNumber());
        Page<PrimaryBankAccount> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PrimaryBankAccountDTO> getPrimaryBankAccountById(Long id) {
        log.debug("Fetching Primary Bank Account with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public PrimaryBankAccountDTO savePrimaryBankAccount(PrimaryBankAccountDTO primaryBankAccountDTO) {
        log.info("Saving new Primary Bank Account");
        PrimaryBankAccount entity = mapper.toEntity(primaryBankAccountDTO);
        PrimaryBankAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public PrimaryBankAccountDTO updatePrimaryBankAccount(Long id, PrimaryBankAccountDTO primaryBankAccountDTO) {
        log.info("Updating Primary Bank Account with ID: {}", id);

        PrimaryBankAccount existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Primary Bank Account not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        PrimaryBankAccount toUpdate = mapper.toEntity(primaryBankAccountDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        PrimaryBankAccount updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deletePrimaryBankAccountById(Long id) {
        log.info("Deleting Primary Bank Account  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Primary Bank Account not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softPrimaryBankAccountServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
