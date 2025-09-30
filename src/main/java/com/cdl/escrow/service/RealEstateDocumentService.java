package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateDocumentDTO;
import com.cdl.escrow.entity.RealEstateDocument;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface RealEstateDocumentService {
    Page<RealEstateDocumentDTO> getAllRealEstateDocument(final Pageable pageable);

    Optional<RealEstateDocumentDTO> getRealEstateDocumentById(Long id);

    RealEstateDocumentDTO saveRealEstateDocument(RealEstateDocumentDTO realEstateDocumentDTO);

    RealEstateDocumentDTO updateRealEstateDocument(Long id, RealEstateDocumentDTO realEstateDocumentDTO);

    Boolean deleteRealEstateDocumentById(Long id);

    RealEstateDocument storeFile(MultipartFile file, String module, Long recordId, String storageType,Long documentTypeId);

    Resource loadFileById(Long id);

    Resource loadFileByName(String module, String fileName) throws IOException;
}
