package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.RealEstateBankAccountDTO;
import com.cdl.escrow.entity.RealEstateBankAccount;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.RealEstateBankAccountMapper;
import com.cdl.escrow.repository.RealEstateBankAccountRepository;
import com.cdl.escrow.service.RealEstateBankAccountService;
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
public class RealEstateBankAccountServiceImpl implements RealEstateBankAccountService {

   private final RealEstateBankAccountRepository repository;

   private final RealEstateBankAccountMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateBankAccountDTO> getAllRealEstateBankAccount(Pageable pageable) {
        log.debug("Fetching all Real Estate Assest bank account , page: {}", pageable.getPageNumber());
        Page<RealEstateBankAccount> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateBankAccountDTO> getRealEstateBankAccountById(Long id) {
        log.debug("Fetching Real EstateAssest bank account with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public RealEstateBankAccountDTO saveRealEstateBankAccount(RealEstateBankAccountDTO realEstateBankAccountDTO) {
        log.info("Saving new Real EstateAssest bank account");
        RealEstateBankAccount entity = mapper.toEntity(realEstateBankAccountDTO);
        RealEstateBankAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public RealEstateBankAccountDTO updateRealEstateBankAccount(Long id, RealEstateBankAccountDTO realEstateBankAccountDTO) {
        log.info("Updating Real EstateAssest bank account with ID: {}", id);

        RealEstateBankAccount existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest bank account not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateBankAccount toUpdate = mapper.toEntity(realEstateBankAccountDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateBankAccount updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteRealEstateBankAccountById(Long id) {
        log.info("Deleting Real EstateAssest bank account  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest bank account not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softRealEstateBankAccountServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
