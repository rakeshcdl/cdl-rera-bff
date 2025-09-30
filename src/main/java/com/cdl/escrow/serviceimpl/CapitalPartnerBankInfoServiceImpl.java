package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.CapitalPartnerBankInfoDTO;
import com.cdl.escrow.entity.CapitalPartnerBankInfo;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerBankInfoMapper;
import com.cdl.escrow.repository.CapitalPartnerBankInfoRepository;
import com.cdl.escrow.service.CapitalPartnerBankInfoService;
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
public class CapitalPartnerBankInfoServiceImpl implements CapitalPartnerBankInfoService {

    private final CapitalPartnerBankInfoRepository repository;

    private final CapitalPartnerBankInfoMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerBankInfoDTO> getAllCapitalPartnerBankInfo(Pageable pageable) {
        log.debug("Fetching all capital partner bank info, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerBankInfo> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerBankInfoDTO> getCapitalPartnerBankInfoById(Long id) {
        log.debug("Fetching capital partner bank info with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public CapitalPartnerBankInfoDTO saveCapitalPartnerBankInfo(CapitalPartnerBankInfoDTO capitalPartnerBankInfoDTO) {
        log.info("Saving new capital partner bank info");
        CapitalPartnerBankInfo entity = mapper.toEntity(capitalPartnerBankInfoDTO);
        CapitalPartnerBankInfo saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public CapitalPartnerBankInfoDTO updateCapitalPartnerBankInfo(Long id, CapitalPartnerBankInfoDTO capitalPartnerBankInfoDTO) {
        log.info("Updating capital partner bank info with ID: {}", id);

        CapitalPartnerBankInfo existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Build capital partner bank info not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        CapitalPartnerBankInfo toUpdate = mapper.toEntity(capitalPartnerBankInfoDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        CapitalPartnerBankInfo updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteCapitalPartnerBankInfoById(Long id) {
        log.info("Deleting capital partner bank info  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Capital partner bank info not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerBankInfoServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
