package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BankBranchDTO;
import com.cdl.escrow.entity.BankBranch;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BankBranchMapper;
import com.cdl.escrow.repository.BankBranchRepository;
import com.cdl.escrow.service.BankBranchService;
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
public class BankBranchServiceImpl implements BankBranchService {

    private final BankBranchRepository repository;

    private final BankBranchMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BankBranchDTO> getAllBankBranch(Pageable pageable) {
        log.debug("Fetching all bank branches, page: {}", pageable.getPageNumber());
        Page<BankBranch> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BankBranchDTO> getBankBranchById(Long id) {
        log.debug("Fetching branch with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public BankBranchDTO saveBankBranch(BankBranchDTO bankBranchDTO) {
        log.info("Saving new branch");
        BankBranch entity = mapper.toEntity(bankBranchDTO);
        BankBranch saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public BankBranchDTO updateBankBranch(Long id, BankBranchDTO bankBranchDTO) {
        log.info("Updating Bank branch with ID: {}", id);

        BankBranch existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Bank Branch not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BankBranch toUpdate = mapper.toEntity(bankBranchDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BankBranch updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteBankBranchById(Long id) {
        log.info("Deleting bank branch with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Bank branch not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBankBranchById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
