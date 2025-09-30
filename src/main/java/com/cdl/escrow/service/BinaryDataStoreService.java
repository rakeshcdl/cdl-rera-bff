package com.cdl.escrow.service;

import com.cdl.escrow.dto.BinaryDataStoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BinaryDataStoreService {
    Page<BinaryDataStoreDTO> getAllBinaryDataStore(final Pageable pageable);

    Optional<BinaryDataStoreDTO> getBinaryDataStoreById(Long id);

    BinaryDataStoreDTO saveBinaryDataStore(BinaryDataStoreDTO binaryDataStoreDTO);

    BinaryDataStoreDTO updateBinaryDataStore(Long id, BinaryDataStoreDTO binaryDataStoreDTO);

    Boolean deleteBinaryDataStoreById(Long id);

    boolean softBinaryDataStoreServiceById(Long id);
}
