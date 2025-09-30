package com.cdl.escrow.serviceimpl;


import com.cdl.escrow.dto.BuildPartnerContactDTO;
import com.cdl.escrow.entity.BuildPartnerContact;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BuildPartnerContactMapper;
import com.cdl.escrow.repository.BuildPartnerContactRepository;
import com.cdl.escrow.service.BuildPartnerContactService;
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
public class BuildPartnerContactServiceImpl implements BuildPartnerContactService {

    private final BuildPartnerContactRepository repository;

    private final BuildPartnerContactMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<BuildPartnerContactDTO> getAllBuildPartnerContact(Pageable pageable) {
        log.debug("Fetching all build partner contact, page: {}", pageable.getPageNumber());
        Page<BuildPartnerContact> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BuildPartnerContactDTO> getBuildPartnerContactById(Long id) {
        log.debug("Fetching build partner contact with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public BuildPartnerContactDTO saveBuildPartnerContact(BuildPartnerContactDTO buildPartnerContactDTO) {
        log.info("Saving new build partner contact");
        BuildPartnerContact entity = mapper.toEntity(buildPartnerContactDTO);
        BuildPartnerContact saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public BuildPartnerContactDTO updateBuildPartnerContact(Long id, BuildPartnerContactDTO buildPartnerContactDTO) {
        log.info("Updating build partner contact with ID: {}", id);

        BuildPartnerContact existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Build partner contact not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BuildPartnerContact toUpdate = mapper.toEntity(buildPartnerContactDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BuildPartnerContact updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteBuildPartnerContactById(Long id) {
        log.info("Deleting build partner contact with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Build partner contact not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBuildPartnerContactServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
