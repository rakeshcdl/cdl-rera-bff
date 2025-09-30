package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.SuretyBondReleaseCriteria;
import com.cdl.escrow.dto.SuretyBondReleaseDTO;
import com.cdl.escrow.entity.SuretyBondRelease;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.SuretyBondReleaseMapper;
import com.cdl.escrow.repository.SuretyBondReleaseRepository;
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
public class SuretyBondReleaseCriteriaService  extends BaseSpecificationBuilder<SuretyBondRelease>  implements Serializable {

    private final transient SuretyBondReleaseRepository suretyBondReleaseRepository;

    private final transient SuretyBondReleaseMapper suretyBondReleaseMapper;

    public Page<SuretyBondReleaseDTO> findByCriteria(SuretyBondReleaseCriteria criteria, Pageable pageable) {
        Specification<SuretyBondRelease> specification = createSpecification(criteria);
        return suretyBondReleaseRepository.findAll(specification, pageable).map(suretyBondReleaseMapper::toDto);
    }

    private Specification<SuretyBondRelease> createSpecification(SuretyBondReleaseCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addZonedDateTimeFilter(cb, root, predicates, "suretyBondReleaseRequestDate", criteria.getSuretyBondReleaseRequestDate());
                addLongFilter(cb, root, predicates, "suretyBondId", criteria.getSuretyBondId());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
