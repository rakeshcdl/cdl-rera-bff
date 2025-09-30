package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BuildPartnerFeesDTO;
import com.cdl.escrow.entity.BuildPartnerFees;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BuildPartnerFeesMapper;
import com.cdl.escrow.repository.BuildPartnerFeesRepository;
import com.cdl.escrow.service.BuildPartnerFeesService;
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
public class BuildPartnerFeesServiceImpl implements BuildPartnerFeesService {

    private final BuildPartnerFeesRepository repository;

    private final BuildPartnerFeesMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<BuildPartnerFeesDTO> getAllBuildPartnerFees(Pageable pageable) {
        log.debug("Fetching all build partner fees, page: {}", pageable.getPageNumber());
        Page<BuildPartnerFees> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BuildPartnerFeesDTO> getBuildPartnerFeesById(Long id) {
        log.debug("Fetching build partner fees with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public BuildPartnerFeesDTO saveBuildPartnerFees(BuildPartnerFeesDTO buildPartnerFeesDTO) {
        log.info("Saving new build partner fees");
        BuildPartnerFees entity = mapper.toEntity(buildPartnerFeesDTO);
        BuildPartnerFees saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public BuildPartnerFeesDTO updateBuildPartnerFees(Long id, BuildPartnerFeesDTO buildPartnerFeesDTO) {
        log.info("Updating build partner fees with ID: {}", id);

        BuildPartnerFees existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Build partner fees not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BuildPartnerFees toUpdate = mapper.toEntity(buildPartnerFeesDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BuildPartnerFees updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteBuildPartnerFeesById(Long id) {
        log.info("Deleting build partner fees with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Build partner fees not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBuildPartnerFeesServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
