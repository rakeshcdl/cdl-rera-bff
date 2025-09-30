package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BuildPartnerAccountDTO;
import com.cdl.escrow.entity.BuildPartnerAccount;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BuildPartnerAccountMapper;
import com.cdl.escrow.repository.BuildPartnerAccountRepository;
import com.cdl.escrow.service.BuildPartnerAccountService;
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
public class BuildPartnerAccountServiceImpl implements BuildPartnerAccountService {
    private final BuildPartnerAccountRepository repository;

    private final BuildPartnerAccountMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BuildPartnerAccountDTO> getAllBuildPartnerAccount(Pageable pageable) {
        log.debug("Fetching all build partner account, page: {}", pageable.getPageNumber());
        Page<BuildPartnerAccount> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BuildPartnerAccountDTO> getBuildPartnerAccountById(Long id) {
        log.debug("Fetching build partner account with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public BuildPartnerAccountDTO saveBuildPartnerAccount(BuildPartnerAccountDTO buildPartnerAccountDTO) {
        log.info("Saving new build partner account");
        BuildPartnerAccount entity = mapper.toEntity(buildPartnerAccountDTO);
        BuildPartnerAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public BuildPartnerAccountDTO updateBuildPartnerAccount(Long id, BuildPartnerAccountDTO buildPartnerAccountDTO) {
        log.info("Updating build partner account with ID: {}", id);

        BuildPartnerAccount existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Build partner account not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BuildPartnerAccount toUpdate = mapper.toEntity(buildPartnerAccountDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BuildPartnerAccount updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteBuildPartnerAccountById(Long id) {
        log.info("Deleting build partner account with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Build partner account not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBuildPartnerAccountServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}

