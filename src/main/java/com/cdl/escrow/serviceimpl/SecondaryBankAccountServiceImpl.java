package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.SecondaryBankAccountDTO;
import com.cdl.escrow.entity.SecondaryBankAccount;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.SecondaryBankAccountMapper;
import com.cdl.escrow.repository.SecondaryBankAccountRepository;
import com.cdl.escrow.service.SecondaryBankAccountService;
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
public class SecondaryBankAccountServiceImpl implements SecondaryBankAccountService {

   private final SecondaryBankAccountRepository repository;

    private final SecondaryBankAccountMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<SecondaryBankAccountDTO> getAllSecondaryBankAccount(Pageable pageable) {
        log.debug("Fetching all Secondary BankAccount , page: {}", pageable.getPageNumber());
        Page<SecondaryBankAccount> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SecondaryBankAccountDTO> getSecondaryBankAccountById(Long id) {
        log.debug("Fetching Secondary BankAccount with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public SecondaryBankAccountDTO saveSecondaryBankAccount(SecondaryBankAccountDTO secondaryBankAccountDTO) {
        log.info("Saving new Secondary Bank Account");
        SecondaryBankAccount entity = mapper.toEntity(secondaryBankAccountDTO);
        SecondaryBankAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public SecondaryBankAccountDTO updateSecondaryBankAccount(Long id, SecondaryBankAccountDTO secondaryBankAccountDTO) {
        log.info("Updating Secondary Bank Account with ID: {}", id);

        SecondaryBankAccount existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Secondary Bank Account not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        SecondaryBankAccount toUpdate = mapper.toEntity(secondaryBankAccountDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        SecondaryBankAccount updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteSecondaryBankAccountById(Long id) {
        log.info("Deleting Secondary Bank Account  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Secondary Bank Account not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softSecondaryBankAccountServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}