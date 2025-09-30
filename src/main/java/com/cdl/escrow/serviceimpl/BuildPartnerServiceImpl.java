package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.dto.record.BuildPartnerRecord;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BuildPartnerMapper;
import com.cdl.escrow.mapper.mapstruct.BuildPartnerMapStruct;
import com.cdl.escrow.repository.BuildPartnerRepository;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.BuildPartnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerServiceImpl implements BuildPartnerService {

   private final BuildPartnerRepository repository;

    private final BuildPartnerMapper mapper;

    private final TaskStatusRepository taskStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<BuildPartnerDTO> getAllBuildPartner(Pageable pageable) {
        log.debug("Fetching all build partner, page: {}", pageable.getPageNumber());
        Page<BuildPartner> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BuildPartnerDTO> getBuildPartnerById(Long id) {
        log.debug("Fetching build partner with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public BuildPartnerDTO saveBuildPartner(BuildPartnerDTO buildPartnerDTO) {
        log.info("Saving new build partner");

        BuildPartner entity = mapper.toEntity(buildPartnerDTO);

        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "IN_PROGRESS"));
        entity.setTaskStatus(ts);

        // set any default flags if needed
        entity.setDeleted(false);
        // entity.setEnabled(true/false) as per business rule
         entity.setEnabled(true);

        BuildPartner saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public BuildPartnerDTO updateBuildPartner(Long id, BuildPartnerDTO buildPartnerDTO) {
        log.info("Updating build partner with ID: {}", id);

        BuildPartner existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Build partner not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BuildPartner toUpdate = mapper.toEntity(buildPartnerDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("DRAFT")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "DRAFT"));
        toUpdate.setTaskStatus(ts);
        BuildPartner updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteBuildPartnerById(Long id) {
        log.info("Deleting build partner  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Build partner not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public void finalizeBuildPartner(Long moduleId, TaskStatus status) {

            BuildPartner partner =  repository.findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("Build Partner not found: " + moduleId));

        partner.setTaskStatus(status);
        repository.save(partner);

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

