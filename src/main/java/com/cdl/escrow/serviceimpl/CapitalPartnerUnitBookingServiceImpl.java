package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.dto.CapitalPartnerUnitBookingDTO;
import com.cdl.escrow.dto.CapitalPartnerUnitDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.entity.CapitalPartnerUnitBooking;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerUnitBookingMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitBookingRepository;
import com.cdl.escrow.repository.CapitalPartnerUnitRepository;
import com.cdl.escrow.service.CapitalPartnerUnitBookingService;
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
public class CapitalPartnerUnitBookingServiceImpl implements CapitalPartnerUnitBookingService {

    private final CapitalPartnerUnitBookingRepository repository;

   private final CapitalPartnerUnitBookingMapper mapper;

   private final CapitalPartnerUnitRepository capitalPartnerUnitRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerUnitBookingDTO> getAllCapitalPartnerUnitBooking(Pageable pageable) {
        log.debug("Fetching all capital partner unit booking, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitBooking> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerUnitBookingDTO> getCapitalPartnerUnitBookingById(Long id) {
        log.debug("Fetching capital partner unit booking with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitBookingDTO saveCapitalPartnerUnitBooking(CapitalPartnerUnitBookingDTO dto) {

        log.info("Saving new capital partner unit booking");
      //  CapitalPartnerUnitBooking entity = mapper.toEntity(capitalPartnerUnitBookingDTO);
      //  CapitalPartnerUnitBooking saved = repository.save(entity);


        CapitalPartnerUnitBooking unit = mapper.toEntity(dto);
        CapitalPartnerUnitBooking savedUnit = repository.save(unit);

        // 3. If DTO has capitalPartnerDTOS, update owning side
        if (dto.getCapitalPartnerUnitDTOS() != null && !dto.getCapitalPartnerUnitDTOS().isEmpty()) {

            for (CapitalPartnerUnitDTO cpDto : dto.getCapitalPartnerUnitDTOS()) {
                if (cpDto.getId() == null) continue;

                // Load partner as a managed reference (no SELECT)
                Optional<CapitalPartnerUnit> partners = capitalPartnerUnitRepository.findById(cpDto.getId());

                // Set owning side
                if(partners.isPresent())
                {
                    log.info(String.valueOf(partners.get().getId()));
                    partners.get().setCapitalPartnerUnitBooking(savedUnit);
                    capitalPartnerUnitRepository.save(partners.get());
                }
            }

        }
        return mapper.toDto(savedUnit);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitBookingDTO updateCapitalPartnerUnitBooking(Long id, CapitalPartnerUnitBookingDTO dto) {
        log.info("Updating capital partner unit booking with ID: {}", id);

        CapitalPartnerUnitBooking existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Capital partner unit booking not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
       /* CapitalPartnerUnitBooking toUpdate = mapper.toEntity(dto);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        CapitalPartnerUnitBooking updated = repository.save(toUpdate);*/


        CapitalPartnerUnitBooking unit = mapper.toEntity(dto);
        unit.setId(existing.getId()); // Ensure the correct ID is preserved
        CapitalPartnerUnitBooking savedUnit = repository.save(unit);

        // 3. If DTO has capitalPartnerDTOS, update owning side
        if (dto.getCapitalPartnerUnitDTOS() != null && !dto.getCapitalPartnerUnitDTOS().isEmpty()) {

            for (CapitalPartnerUnitDTO cpDto : dto.getCapitalPartnerUnitDTOS()) {
                if (cpDto.getId() == null) continue;

                // Load partner as a managed reference (no SELECT)
                Optional<CapitalPartnerUnit> partners = capitalPartnerUnitRepository.findById(cpDto.getId());

                // Set owning side
                if(partners.isPresent())
                {
                    log.info(String.valueOf(partners.get().getId()));
                    partners.get().setCapitalPartnerUnitBooking(savedUnit);
                    capitalPartnerUnitRepository.save(partners.get());
                }
            }

        }
        return mapper.toDto(savedUnit);



       // return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteCapitalPartnerUnitBookingById(Long id) {
        log.info("Deleting capital partner unit booking  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Capital partner unit booking not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerUnitBookingServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
