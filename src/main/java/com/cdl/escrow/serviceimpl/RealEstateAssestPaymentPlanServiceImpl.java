package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.RealEstateAssestPaymentPlanDTO;
import com.cdl.escrow.entity.RealEstateAssestPaymentPlan;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.RealEstateAssestPaymentPlanMapper;
import com.cdl.escrow.repository.RealEstateAssestPaymentPlanRepository;
import com.cdl.escrow.service.RealEstateAssestPaymentPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestPaymentPlanServiceImpl implements RealEstateAssestPaymentPlanService {

    private final RealEstateAssestPaymentPlanRepository repository;

    private final RealEstateAssestPaymentPlanMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateAssestPaymentPlanDTO> getAllRealEstateAssestPaymentPlan(Pageable pageable) {
        log.debug("Fetching all Real Estate Assest Payment Plan, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestPaymentPlan> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateAssestPaymentPlanDTO> getRealEstateAssestPaymentPlanById(Long id) {
        log.debug("Fetching Real Estate Assest Payment Plan with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public RealEstateAssestPaymentPlanDTO saveRealEstateAssestPaymentPlan(RealEstateAssestPaymentPlanDTO realEstateAssestPaymentPlanDTO) {
        log.info("Saving new Real Estate Assest Payment Plan");
        RealEstateAssestPaymentPlan entity = mapper.toEntity(realEstateAssestPaymentPlanDTO);
        RealEstateAssestPaymentPlan saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public RealEstateAssestPaymentPlanDTO updateRealEstateAssestPaymentPlan(Long id, RealEstateAssestPaymentPlanDTO realEstateAssestPaymentPlanDTO) {
        log.info("Updating Real EstateAssest Payment Plan with ID: {}", id);

        RealEstateAssestPaymentPlan existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest Payment Plan not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateAssestPaymentPlan toUpdate = mapper.toEntity(realEstateAssestPaymentPlanDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateAssestPaymentPlan updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteRealEstateAssestPaymentPlanById(Long id) {
        log.info("Deleting Real EstateAssest Payment Plan with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest Payment Plan not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public RealEstateAssestPaymentPlanDTO saveAllRealEstateAssestPaymentPlan(List<RealEstateAssestPaymentPlanDTO> dto) {
        RealEstateAssestPaymentPlan saved = null;
        for(RealEstateAssestPaymentPlanDTO realEstateAssestPaymentPlanDTO : dto) {
            RealEstateAssestPaymentPlan entity = mapper.toEntity(realEstateAssestPaymentPlanDTO);
             saved = repository.save(entity);
        }
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public boolean softRealEstateAssestPaymentPlanServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
