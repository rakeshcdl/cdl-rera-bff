package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.RealEstateDocumentCriteria;
import com.cdl.escrow.dto.RealEstateDocumentDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.RealEstateDocument;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateDocumentMapper;
import com.cdl.escrow.repository.RealEstateDocumentRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealEstateDocumentCriteriaService extends BaseSpecificationBuilder<RealEstateDocument>  implements Serializable {

    private final transient RealEstateDocumentRepository realEstateDocumentRepository;

    private final transient RealEstateDocumentMapper realEstateDocumentMapper;

    public Page<RealEstateDocumentDTO> findByCriteria(RealEstateDocumentCriteria criteria, Pageable pageable) {
        Specification<RealEstateDocument> specification = createSpecification(criteria);
        return realEstateDocumentRepository.findAll(specification, pageable).map(realEstateDocumentMapper::toDto);
    }

    private Specification<RealEstateDocument> createSpecification(RealEstateDocumentCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "rea", criteria.getRea(), true);
                addStringFilter(cb, root, predicates, "documentName", criteria.getDocumentName(), true);
                addStringFilter(cb, root, predicates, "content", criteria.getContent(), true);
                addStringFilter(cb, root, predicates, "location", criteria.getLocation(), true);
                addStringFilter(cb, root, predicates, "module", criteria.getModule(), true);
                addLongFilter(cb, root, predicates, "recordId", criteria.getRecordId());
                addStringFilter(cb, root, predicates, "storageType", criteria.getStorageType(), true);
                addZonedDateTimeFilter(cb, root, predicates, "uploadDate", criteria.getUploadDate());
                addStringFilter(cb, root, predicates, "documentSize", criteria.getDocumentSize(), true);
                addZonedDateTimeFilter(cb, root, predicates, "validityDate", criteria.getValidityDate());
                addStringFilter(cb, root, predicates, "eventDetail", criteria.getEventDetail(), true);


               // addLongFilter(cb, root, predicates, "documentTypeId", criteria.getDocumentTypeId());

                if (criteria.getDocumentTypeId() != null) {
                    Join<RealEstateDocument, ApplicationSetting> join = root.join("documentType", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getDocumentTypeId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
