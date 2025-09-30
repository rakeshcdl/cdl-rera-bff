package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.ApplicationUserDTO;
import com.cdl.escrow.entity.ApplicationUser;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationUserMapper;
import com.cdl.escrow.repository.ApplicationUserRepository;
import com.cdl.escrow.service.ApplicationUserService;
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
public class ApplicationUserServiceImpl implements ApplicationUserService {

   private final ApplicationUserRepository repository;

   private final ApplicationUserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationUserDTO> getAllApplicationUser(Pageable pageable) {
        log.debug("Fetching all application user, page: {}", pageable.getPageNumber());
        Page<ApplicationUser> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationUserDTO> getApplicationUserById(Long id) {
        log.debug("Fetching application user with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public ApplicationUserDTO saveApplicationUser(ApplicationUserDTO applicationUserDTO) {
        log.info("Saving new application user");
        ApplicationUser entity = mapper.toEntity(applicationUserDTO);
        ApplicationUser saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public ApplicationUserDTO updateApplicationUser(Long id, ApplicationUserDTO applicationUserDTO) {
        log.info("Updating application user with ID: {}", id);

        ApplicationUser existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Application user not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ApplicationUser toUpdate = mapper.toEntity(applicationUserDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ApplicationUser updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteApplicationUserById(Long id) {
        log.info("Deleting application user with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Setting not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }
}
