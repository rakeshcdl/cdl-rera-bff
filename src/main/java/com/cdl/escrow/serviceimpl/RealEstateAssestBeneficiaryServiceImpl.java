package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.dto.RealEstateAssestBeneficiaryDTO;
import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.BuildPartnerBeneficiary;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestBeneficiary;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.RealEstateAssestBeneficiaryMapper;
import com.cdl.escrow.repository.RealEstateAssestBeneficiaryRepository;
import com.cdl.escrow.repository.RealEstateAssestRepository;
import com.cdl.escrow.service.RealEstateAssestBeneficiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestBeneficiaryServiceImpl implements RealEstateAssestBeneficiaryService {

    private final RealEstateAssestBeneficiaryRepository repository;

    private final RealEstateAssestBeneficiaryMapper mapper;

    private final RealEstateAssestRepository realEstateAssestRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateAssestBeneficiaryDTO> getAllRealEstateAssestBeneficiary(Pageable pageable) {
        log.debug("Fetching all Real EstateAssest Beneficiary , page: {}", pageable.getPageNumber());
        Page<RealEstateAssestBeneficiary> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateAssestBeneficiaryDTO> getRealEstateAssestBeneficiaryById(Long id) {
        log.debug("Fetching Real EstateAssest Beneficiary with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


//    @Override
//    @Transactional
//    public RealEstateAssestBeneficiaryDTO saveRealEstateAssestBeneficiary(RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO) {
//        log.info("Saving new Real EstateAssest Beneficiary");
//        RealEstateAssestBeneficiary entity = mapper.toEntity(realEstateAssestBeneficiaryDTO);
//        RealEstateAssestBeneficiary saved = repository.save(entity);
//        return mapper.toDto(saved);
//    }


    @Override
    @Transactional
    public RealEstateAssestBeneficiaryDTO saveRealEstateAssestBeneficiary(RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO) {
        log.info("Saving new real estate beneficiary");

        // Step 1: Save the main entity without associations
        RealEstateAssestBeneficiary entity = mapper.toEntity(realEstateAssestBeneficiaryDTO);
        entity.setRealEstateAssests(new HashSet<>()); // Clear associations temporarily
        RealEstateAssestBeneficiary saved = repository.save(entity);

        // Step 2: Add associations and save again
        if (realEstateAssestBeneficiaryDTO.getRealEstateAssestDTO() != null &&
                !realEstateAssestBeneficiaryDTO.getRealEstateAssestDTO().isEmpty()) {

            Set<RealEstateAssest> realEstateAssests = new HashSet<>();

            for (RealEstateAssestDTO bpDto : realEstateAssestBeneficiaryDTO.getRealEstateAssestDTO()) {
                RealEstateAssest bp = realEstateAssestRepository.findById(bpDto.getId())
                        .orElseThrow(() -> new RuntimeException("Real Estate Assest not found"));
                realEstateAssests.add(bp);

                // Set bidirectional relationship
                bp.getRealEstateAssestBeneficiaries().add(saved);
            }

            saved.setRealEstateAssests(realEstateAssests);
            saved = repository.save(saved);

            // Save the other side as well
            realEstateAssestRepository.saveAll(realEstateAssests);
        }

        return mapper.toDto(saved);
    }


    /*@Override
    @Transactional
    public RealEstateAssestBeneficiaryDTO updateRealEstateAssestBeneficiary(Long id, RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO) {
        log.info("Updating Real EstateAssest Beneficiary with ID: {}", id);

        RealEstateAssestBeneficiary existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest Beneficiary not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateAssestBeneficiary toUpdate = mapper.toEntity(realEstateAssestBeneficiaryDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateAssestBeneficiary updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }*/

    @Override
    @Transactional
    public RealEstateAssestBeneficiaryDTO updateRealEstateAssestBeneficiary(Long id, RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO) {
        log.info("Updating real estate beneficiary with id: {}", id);

        // Step 1: Get existing entity
        RealEstateAssestBeneficiary existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RealEstateAssestBeneficiary not found with id: " + id));

        // Step 2: Clear existing associations from both sides
        if (existingEntity.getRealEstateAssests() != null) {
            for (RealEstateAssest existingAssest : existingEntity.getRealEstateAssests()) {
                existingAssest.getRealEstateAssestBeneficiaries().remove(existingEntity);
            }
            realEstateAssestRepository.saveAll(existingEntity.getRealEstateAssests());
            existingEntity.getRealEstateAssests().clear();
        }

        // Step 3: Update entity properties (excluding associations)
        RealEstateAssestBeneficiary updatedEntity = mapper.toEntity(realEstateAssestBeneficiaryDTO);
        updatedEntity.setId(existingEntity.getId()); // Preserve the ID
        updatedEntity.setRealEstateAssests(new HashSet<>()); // Clear associations temporarily

        RealEstateAssestBeneficiary saved = repository.save(updatedEntity);

        // Step 4: Add new associations
        if (realEstateAssestBeneficiaryDTO.getRealEstateAssestDTO() != null &&
                !realEstateAssestBeneficiaryDTO.getRealEstateAssestDTO().isEmpty()) {

            Set<RealEstateAssest> realEstateAssests = new HashSet<>();

            for (RealEstateAssestDTO assestDto : realEstateAssestBeneficiaryDTO.getRealEstateAssestDTO()) {
                RealEstateAssest assest = realEstateAssestRepository.findById(assestDto.getId())
                        .orElseThrow(() -> new RuntimeException("Real Estate Assest not found with id: " + assestDto.getId()));
                realEstateAssests.add(assest);

                // Set bidirectional relationship
                assest.getRealEstateAssestBeneficiaries().add(saved);
            }

            saved.setRealEstateAssests(realEstateAssests);
            saved = repository.save(saved);

            // Save the other side as well
            realEstateAssestRepository.saveAll(realEstateAssests);
        }

        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public Boolean deleteRealEstateAssestBeneficiaryById(Long id) {
        log.info("Deleting Real EstateAssest Beneficiary  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest Beneficiary not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softRealEstateAssestBeneficiaryServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}