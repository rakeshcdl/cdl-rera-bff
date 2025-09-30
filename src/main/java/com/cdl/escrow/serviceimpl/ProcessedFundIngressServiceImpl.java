package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import com.cdl.escrow.entity.ProcessedFundIngress;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ProcessedFundIngressMapper;
import com.cdl.escrow.repository.ProcessedFundIngressRepository;
import com.cdl.escrow.service.ProcessedFundIngressService;
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
public class ProcessedFundIngressServiceImpl implements ProcessedFundIngressService {


    private final ProcessedFundIngressRepository repository;

    private final ProcessedFundIngressMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessedFundIngressDTO> getAllProcessedFundIngress(Pageable pageable) {
        log.debug("Fetching all Processed Fund Egress , page: {}", pageable.getPageNumber());
        Page<ProcessedFundIngress> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessedFundIngressDTO> getProcessedFundIngressById(Long id) {
        log.debug("Fetching Processed Fund Egress with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public ProcessedFundIngressDTO saveProcessedFundIngress(ProcessedFundIngressDTO processedFundIngressDTO) {
        log.info("Saving new Processed Fund Egress");
        ProcessedFundIngress entity = mapper.toEntity(processedFundIngressDTO);
        ProcessedFundIngress saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public ProcessedFundIngressDTO updateProcessedFundIngress(Long id, ProcessedFundIngressDTO processedFundIngressDTO) {
        log.info("Updating Processed Fund Egress with ID: {}", id);

        ProcessedFundIngress existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Processed Fund Egress unit not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ProcessedFundIngress toUpdate = mapper.toEntity(processedFundIngressDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ProcessedFundIngress updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteProcessedFundIngressById(Long id) {
        log.info("Deleting Processed Fund Egress  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Processed Fund Egress not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softProcessedFundIngressServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
