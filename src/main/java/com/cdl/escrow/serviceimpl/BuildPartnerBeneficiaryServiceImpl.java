package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.BuildPartnerBeneficiary;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BuildPartnerBeneficiaryMapper;
import com.cdl.escrow.repository.BuildPartnerBeneficiaryRepository;
import com.cdl.escrow.repository.BuildPartnerRepository;
import com.cdl.escrow.service.BuildPartnerBeneficiaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerBeneficiaryServiceImpl implements BuildPartnerBeneficiaryService {

   private final BuildPartnerBeneficiaryRepository repository;

   private final BuildPartnerBeneficiaryMapper mapper;

   private final BuildPartnerRepository buildPartnerRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<BuildPartnerBeneficiaryDTO> getAllBuildPartnerBeneficiary(Pageable pageable) {
        log.debug("Fetching all build partner beneficiary, page: {}", pageable.getPageNumber());
        Page<BuildPartnerBeneficiary> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BuildPartnerBeneficiaryDTO> getBuildPartnerBeneficiaryById(Long id) {
        log.debug("Fetching build partner beneficiary with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


 /*  @Override
    @Transactional
    public BuildPartnerBeneficiaryDTO saveBuildPartnerBeneficiary(BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO) {
        log.info("Saving new build partner beneficiary");
        BuildPartnerBeneficiary entity = mapper.toEntity(buildPartnerBeneficiaryDTO);
        BuildPartnerBeneficiary saved = repository.save(entity);
        return mapper.toDto(saved);
    }*/

    @Override
    @Transactional
    public BuildPartnerBeneficiaryDTO saveBuildPartnerBeneficiary(BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO) {
        log.info("Saving new build partner beneficiary");

        // Step 1: Save the main entity without associations
        BuildPartnerBeneficiary entity = mapper.toEntity(buildPartnerBeneficiaryDTO);
        entity.setBuildPartners(new HashSet<>()); // Clear associations temporarily
        BuildPartnerBeneficiary saved = repository.save(entity);

        // Step 2: Add associations and save again
        if (buildPartnerBeneficiaryDTO.getBuildPartnerDTO() != null &&
                !buildPartnerBeneficiaryDTO.getBuildPartnerDTO().isEmpty()) {

            Set<BuildPartner> buildPartners = new HashSet<>();

            for (BuildPartnerDTO bpDto : buildPartnerBeneficiaryDTO.getBuildPartnerDTO()) {
                BuildPartner bp = buildPartnerRepository.findById(bpDto.getId())
                        .orElseThrow(() -> new RuntimeException("BuildPartner not found"));
                buildPartners.add(bp);

                // Set bidirectional relationship
                bp.getBuildPartnerBeneficiaries().add(saved);
            }

            saved.setBuildPartners(buildPartners);
            saved = repository.save(saved);

            // Save the other side as well
            buildPartnerRepository.saveAll(buildPartners);
        }

        return mapper.toDto(saved);
    }




   /* @Override
    @Transactional
    public BuildPartnerBeneficiaryDTO updateBuildPartnerBeneficiary(Long id, BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO) {
        log.info("Updating build partner beneficiary with ID: {}", id);

        BuildPartnerBeneficiary existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Build partner beneficiary not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BuildPartnerBeneficiary toUpdate = mapper.toEntity(buildPartnerBeneficiaryDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BuildPartnerBeneficiary updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }*/

    @Override
    @Transactional
    public BuildPartnerBeneficiaryDTO updateBuildPartnerBeneficiary(Long id, BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO) {
        log.info("Updating build partner beneficiary with id: {}", id);

        // Step 1: Get existing entity
        BuildPartnerBeneficiary existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("BuildPartnerBeneficiary not found with id: " + id));

        // Step 2: Clear existing associations from both sides
        if (existingEntity.getBuildPartners() != null) {
            for (BuildPartner existingPartner : existingEntity.getBuildPartners()) {
                existingPartner.getBuildPartnerBeneficiaries().remove(existingEntity);
            }
            buildPartnerRepository.saveAll(existingEntity.getBuildPartners());
            existingEntity.getBuildPartners().clear();
        }

        // Step 3: Update entity properties (excluding associations)
        BuildPartnerBeneficiary updatedEntity = mapper.toEntity(buildPartnerBeneficiaryDTO);
        updatedEntity.setId(existingEntity.getId()); // Preserve the ID
        updatedEntity.setBuildPartners(new HashSet<>()); // Clear associations temporarily

        BuildPartnerBeneficiary saved = repository.save(updatedEntity);

        // Step 4: Add new associations
        if (buildPartnerBeneficiaryDTO.getBuildPartnerDTO() != null &&
                !buildPartnerBeneficiaryDTO.getBuildPartnerDTO().isEmpty()) {

            Set<BuildPartner> buildPartners = new HashSet<>();

            for (BuildPartnerDTO bpDto : buildPartnerBeneficiaryDTO.getBuildPartnerDTO()) {
                BuildPartner bp = buildPartnerRepository.findById(bpDto.getId())
                        .orElseThrow(() -> new RuntimeException("BuildPartner not found with id: " + bpDto.getId()));
                buildPartners.add(bp);

                // Set bidirectional relationship
                bp.getBuildPartnerBeneficiaries().add(saved);
            }

            saved.setBuildPartners(buildPartners);
            saved = repository.save(saved);

            // Save the other side as well
            buildPartnerRepository.saveAll(buildPartners);
        }

        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public Boolean deleteBuildPartnerBeneficiaryById(Long id) {
        log.info("Deleting build partner beneficiary with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Build partner beneficiary not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBuildPartnerBeneficiaryServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }


}
