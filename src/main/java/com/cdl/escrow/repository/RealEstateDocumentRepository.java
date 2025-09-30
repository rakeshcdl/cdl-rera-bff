package com.cdl.escrow.repository;

import com.cdl.escrow.entity.RealEstateDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateDocumentRepository extends JpaRepository<RealEstateDocument,Long> , JpaSpecificationExecutor<RealEstateDocument> {
}
