package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.CapitalPartnerPaymentPlanDTO;
import com.cdl.escrow.entity.CapitalPartnerPaymentPlan;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerPaymentPlanMapper;
import com.cdl.escrow.repository.CapitalPartnerPaymentPlanRepository;
import com.cdl.escrow.service.CapitalPartnerPaymentPlanService;
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
public class CapitalPartnerPaymentPlanServiceImpl  implements CapitalPartnerPaymentPlanService {

    private final CapitalPartnerPaymentPlanRepository repository;

    private final CapitalPartnerPaymentPlanMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerPaymentPlanDTO> getAllCapitalPartnerPaymentPlan(Pageable pageable) {
        log.debug("Fetching all Real Estate Assest Payment Plan, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerPaymentPlan> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerPaymentPlanDTO> getRCapitalPartnerPaymentPlanById(Long id) {
        log.debug("Fetching Real Estate Assest Payment Plan with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public CapitalPartnerPaymentPlanDTO saveCapitalPartnerPaymentPlan(CapitalPartnerPaymentPlanDTO capitalPartnerPaymentPlanDTO) {
        log.info("Saving new Real Estate Assest Payment Plan");
        CapitalPartnerPaymentPlan entity = mapper.toEntity(capitalPartnerPaymentPlanDTO);
        CapitalPartnerPaymentPlan saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public CapitalPartnerPaymentPlanDTO updateCapitalPartnerPaymentPlan(Long id, CapitalPartnerPaymentPlanDTO capitalPartnerPaymentPlanDTO) {
        log.info("Updating Real EstateAssest Payment Plan with ID: {}", id);

        CapitalPartnerPaymentPlan existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest Payment Plan not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        CapitalPartnerPaymentPlan toUpdate = mapper.toEntity(capitalPartnerPaymentPlanDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        CapitalPartnerPaymentPlan updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteCapitalPartnerPaymentPlanById(Long id) {
        log.info("Deleting Real EstateAssest Payment Plan with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest Payment Plan not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public CapitalPartnerPaymentPlanDTO saveAllCapitalPartnerPaymentPlan(List<CapitalPartnerPaymentPlanDTO> capitalPartnerPaymentPlanDTOS) {
        CapitalPartnerPaymentPlan saved = null;
        for(CapitalPartnerPaymentPlanDTO capitalPartnerPaymentPlanDTO : capitalPartnerPaymentPlanDTOS) {
            CapitalPartnerPaymentPlan entity = mapper.toEntity(capitalPartnerPaymentPlanDTO);
            saved = repository.save(entity);
        }
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerPaymentPlanServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
