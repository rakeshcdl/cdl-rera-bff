package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.RealEstateAssestClosureDTO;
import com.cdl.escrow.entity.RealEstateAssestClosure;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.RealEstateAssestClosureMapper;
import com.cdl.escrow.repository.RealEstateAssestClosureRepository;
import com.cdl.escrow.service.RealEstateAssestClosureService;
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
public class RealEstateAssestClosureServiceImpl implements RealEstateAssestClosureService {

   private final RealEstateAssestClosureRepository repository;

    private final RealEstateAssestClosureMapper mapper;



    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateAssestClosureDTO> getAllRealEstateAssestClosure(Pageable pageable) {
        log.debug("Fetching all Real EstateAssest closure , page: {}", pageable.getPageNumber());
        Page<RealEstateAssestClosure> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RealEstateAssestClosureDTO> getRealEstateAssestClosureById(Long id) {
        log.debug("Fetching Real EstateAssest closure with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public RealEstateAssestClosureDTO saveRealEstateAssestClosure(RealEstateAssestClosureDTO realEstateAssestClosureDTO) {
        log.info("Saving new Real EstateAssest closure");
        RealEstateAssestClosure entity = mapper.toEntity(realEstateAssestClosureDTO);
        RealEstateAssestClosure saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public RealEstateAssestClosureDTO updateRealEstateAssestClosure(Long id, RealEstateAssestClosureDTO realEstateAssestClosureDTO) {
        log.info("Updating Real EstateAssest closure with ID: {}", id);

        RealEstateAssestClosure existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Real EstateAssest closure not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        RealEstateAssestClosure toUpdate = mapper.toEntity(realEstateAssestClosureDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        RealEstateAssestClosure updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteRealEstateAssestClosureById(Long id) {
        log.info("Deleting Real EstateAssest closure  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Real EstateAssest closure not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softRealEstateAssestClosureServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
