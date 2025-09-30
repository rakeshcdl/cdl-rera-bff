package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.BinaryDataStoreDTO;
import com.cdl.escrow.entity.BinaryDataStore;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.BinaryDataStoreMapper;
import com.cdl.escrow.repository.BinaryDataStoreRepository;
import com.cdl.escrow.service.BinaryDataStoreService;
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
public class BinaryDataStoreServiceImpl implements BinaryDataStoreService {
    private final BinaryDataStoreRepository repository;

    private final BinaryDataStoreMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BinaryDataStoreDTO> getAllBinaryDataStore(Pageable pageable) {
        log.debug("Fetching all binary data store, page: {}", pageable.getPageNumber());
        Page<BinaryDataStore> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BinaryDataStoreDTO> getBinaryDataStoreById(Long id) {
        log.debug("Fetching binary data store with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public BinaryDataStoreDTO saveBinaryDataStore(BinaryDataStoreDTO binaryDataStoreDTO) {
        log.info("Saving new binary data store");
        BinaryDataStore entity = mapper.toEntity(binaryDataStoreDTO);
        BinaryDataStore saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public BinaryDataStoreDTO updateBinaryDataStore(Long id, BinaryDataStoreDTO binaryDataStoreDTO) {
        log.info("Updating binary data with ID: {}", id);

        BinaryDataStore existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Binary data store not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        BinaryDataStore toUpdate = mapper.toEntity(binaryDataStoreDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        BinaryDataStore updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteBinaryDataStoreById(Long id) {
        log.info("Deleting binary data store with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Binary data store not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softBinaryDataStoreServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}