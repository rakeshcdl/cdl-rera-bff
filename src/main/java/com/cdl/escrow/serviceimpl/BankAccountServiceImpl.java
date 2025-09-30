

package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BankAccountDTO;
import com.cdl.escrow.entity.BankAccount;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BankAccountMapper;
import com.cdl.escrow.repository.BankAccountRepository;
import com.cdl.escrow.service.BankAccountService;
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
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;

    private final BankAccountMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BankAccountDTO> getAllBankAccount(Pageable pageable) {
        log.debug("Fetching all bank account , page: {}", pageable.getPageNumber());
        Page<BankAccount> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankAccountDTO> getBankAccountById(Long id) {
        log.debug("Fetching bank account with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public BankAccountDTO saveBankAccount(BankAccountDTO bankAccountDTO) {
        log.info("Saving new bank account");
        BankAccount entity = mapper.toEntity(bankAccountDTO);
        BankAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BankAccountDTO updateBankAccount(Long id, BankAccountDTO bankAccountDTO) {
        log.info("Updating bank account with ID: {}", id);

        BankAccount existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Bank account not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BankAccount toUpdate = mapper.toEntity(bankAccountDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BankAccount updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteBankAccountById(Long id) {
        log.info("Deleting bank account  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Bank account not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }
}
