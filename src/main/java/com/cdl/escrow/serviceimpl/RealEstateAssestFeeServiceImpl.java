package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.RealEstateAssestFeeDTO;
import com.cdl.escrow.entity.RealEstateAssestFee;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.RealEstateAssestFeeMapper;
import com.cdl.escrow.repository.RealEstateAssestFeeRepository;
import com.cdl.escrow.service.RealEstateAssestFeeService;
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
public class RealEstateAssestFeeServiceImpl implements RealEstateAssestFeeService {

   private final RealEstateAssestFeeRepository repository;

   private final RealEstateAssestFeeMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateAssestFeeDTO> getAllRealEstateAssestFee(Pageable pageable) {
        log.debug("Fetching all Real EstateAssest fee , page: {}", pageable.getPageNumber());
        Page<RealEstateAssestFee> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateAssestFeeDTO> getRealEstateAssestFeeById(Long id) {
        log.debug("Fetching Real EstateAssest fee with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public RealEstateAssestFeeDTO saveRealEstateAssestFee(RealEstateAssestFeeDTO realEstateAssestFeeDTO) {
        log.info("Saving new Real EstateAssest fee");
        RealEstateAssestFee entity = mapper.toEntity(realEstateAssestFeeDTO);
        RealEstateAssestFee saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public RealEstateAssestFeeDTO updateRealEstateAssestFee(Long id, RealEstateAssestFeeDTO realEstateAssestFeeDTO) {
        log.info("Updating Real EstateAssest fee with ID: {}", id);

        RealEstateAssestFee existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest fee not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateAssestFee toUpdate = mapper.toEntity(realEstateAssestFeeDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateAssestFee updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteRealEstateAssestFeeById(Long id) {
        log.info("Deleting Real EstateAssest Beneficiary  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest Beneficiary not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softRealEstateAssestFeeServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
