package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.dto.CapitalPartnerUnitDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerMapper;
import com.cdl.escrow.mapper.CapitalPartnerUnitMapper;
import com.cdl.escrow.repository.CapitalPartnerRepository;
import com.cdl.escrow.repository.CapitalPartnerUnitRepository;
import com.cdl.escrow.service.CapitalPartnerUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerUnitServiceImpl implements CapitalPartnerUnitService {

   private final CapitalPartnerUnitRepository repository;

    private final CapitalPartnerUnitMapper mapper;

    private final CapitalPartnerRepository capitalPartnerRepository;

    private final CapitalPartnerMapper capitalPartnerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerUnitDTO> getAllCapitalPartnerUnit(Pageable pageable) {
        log.debug("Fetching all capital partner unit , page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnit> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerUnitDTO> getCapitalPartnerUnitById(Long id) {
        log.debug("Fetching capital partner unit with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitDTO saveCapitalPartnerUnit(CapitalPartnerUnitDTO dto) {
        log.info("Saving new capital partner unit");
        CapitalPartnerUnit unit = mapper.toEntity(dto);
        CapitalPartnerUnit savedUnit = repository.save(unit);

        // 3. If DTO has capitalPartnerDTOS, update owning side
        if (dto.getCapitalPartnerDTOS() != null && !dto.getCapitalPartnerDTOS().isEmpty()) {

            for (CapitalPartnerDTO cpDto : dto.getCapitalPartnerDTOS()) {
                if (cpDto.getId() == null) continue;

                // Load partner as a managed reference (no SELECT)
                Optional<CapitalPartner> partners = capitalPartnerRepository.findById(cpDto.getId());

                // Set owning side
                if(partners.isPresent())
                {
                    log.info(String.valueOf(partners.get().getId()));
                    partners.get().setCapitalPartnerUnit(savedUnit);
                    capitalPartnerRepository.save(partners.get());
                }
            }

        }

        return mapper.toDto(savedUnit);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitDTO updateCapitalPartnerUnit(Long id, CapitalPartnerUnitDTO capitalPartnerUnitDTO) {
        log.info("Updating capital partner unit with ID: {}", id);

        CapitalPartnerUnit existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Capital partner unit not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
      //  CapitalPartnerUnit toUpdate = mapper.toEntity(capitalPartnerUnitDTO);


      //  CapitalPartnerUnit updated = repository.save(toUpdate);

        CapitalPartnerUnit unit = mapper.toEntity(capitalPartnerUnitDTO);
        unit.setId(existing.getId()); // Ensure the correct ID is preserved
        CapitalPartnerUnit savedUnit = repository.save(unit);

        // 3. If DTO has capitalPartnerDTOS, update owning side
        if (capitalPartnerUnitDTO.getCapitalPartnerDTOS() != null && !capitalPartnerUnitDTO.getCapitalPartnerDTOS().isEmpty()) {

            for (CapitalPartnerDTO cpDto : capitalPartnerUnitDTO.getCapitalPartnerDTOS()) {
                if (cpDto.getId() == null) continue;

                // Load partner as a managed reference (no SELECT)
                Optional<CapitalPartner> partners = capitalPartnerRepository.findById(cpDto.getId());

                // Set owning side
                if(partners.isPresent())
                {
                    log.info(String.valueOf(partners.get().getId()));
                    partners.get().setCapitalPartnerUnit(savedUnit);
                    capitalPartnerRepository.save(partners.get());
                }
            }

        }

        return mapper.toDto(savedUnit);
    }


    @Override
    @Transactional
    public Boolean deleteCapitalPartnerUnitById(Long id) {
        log.info("Deleting capital partner unit  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Capital partner unit not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerUnitServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}