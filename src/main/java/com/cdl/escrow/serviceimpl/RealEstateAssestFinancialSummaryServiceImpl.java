package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.RealEstateAssestFinancialSummaryDTO;
import com.cdl.escrow.entity.RealEstateAssestFinancialSummary;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.RealEstateAssestFinancialSummaryMapper;
import com.cdl.escrow.repository.RealEstateAssestFinancialSummaryRepository;
import com.cdl.escrow.service.RealEstateAssestFinancialSummaryService;
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
public class RealEstateAssestFinancialSummaryServiceImpl implements RealEstateAssestFinancialSummaryService {

    private final RealEstateAssestFinancialSummaryRepository repository;

    private final RealEstateAssestFinancialSummaryMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateAssestFinancialSummaryDTO> getAllRealEstateAssestFinancialSummary(Pageable pageable) {
        log.debug("Fetching all Real EstateAssest financial summary , page: {}", pageable.getPageNumber());
        Page<RealEstateAssestFinancialSummary> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateAssestFinancialSummaryDTO> getRealEstateAssestFinancialSummaryById(Long id) {
        log.debug("Fetching Real EstateAssest financial summary with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public RealEstateAssestFinancialSummaryDTO saveRealEstateAssestFinancialSummary(RealEstateAssestFinancialSummaryDTO realEstateAssestFinancialSummaryDTO) {
        log.info("Saving new Real EstateAssest financial summary");
        RealEstateAssestFinancialSummary entity = mapper.toEntity(realEstateAssestFinancialSummaryDTO);
        RealEstateAssestFinancialSummary saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public RealEstateAssestFinancialSummaryDTO updateRealEstateAssestFinancialSummary(Long id, RealEstateAssestFinancialSummaryDTO realEstateAssestFinancialSummaryDTO) {
        log.info("Updating Real EstateAssest financial summary with ID: {}", id);

        RealEstateAssestFinancialSummary existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest financial summary not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateAssestFinancialSummary toUpdate = mapper.toEntity(realEstateAssestFinancialSummaryDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateAssestFinancialSummary updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteRealEstateAssestFinancialSummaryById(Long id) {
        log.info("Deleting Real EstateAssest financial summary  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest financial summary not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softRealEstateAssestFinancialSummaryServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
