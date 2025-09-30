package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.RealEstateAssestFeeCriteria;
import com.cdl.escrow.dto.RealEstateAssestFeeDTO;
import com.cdl.escrow.entity.RealEstateAssestFee;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateAssestFeeMapper;
import com.cdl.escrow.repository.RealEstateAssestFeeRepository;
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
public class RealEstateAssestFeeCriteriaService extends BaseSpecificationBuilder<RealEstateAssestFee>  implements Serializable {

    private final transient RealEstateAssestFeeRepository realEstateAssestFeeRepository;

    private final transient RealEstateAssestFeeMapper realEstateAssestFeeMapper;

    public Page<RealEstateAssestFeeDTO> findByCriteria(RealEstateAssestFeeCriteria criteria, Pageable pageable) {
        Specification<RealEstateAssestFee> specification = createSpecification(criteria);
        return realEstateAssestFeeRepository.findAll(specification, pageable).map(realEstateAssestFeeMapper::toDto);
    }

    private Specification<RealEstateAssestFee> createSpecification(RealEstateAssestFeeCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addDoubleFilter(cb, root, predicates, "reafAmount", criteria.getReafAmount());
                addDoubleFilter(cb, root, predicates, "reafTotalAmount", criteria.getReafTotalAmount());
                addZonedDateTimeFilter(cb, root, predicates, "reafCalender", criteria.getReafCalender());
                addZonedDateTimeFilter(cb, root, predicates, "reafNextRecoveryDate", criteria.getReafNextRecoveryDate());
                addDoubleFilter(cb, root, predicates, "reafVatPercentage", criteria.getReafVatPercentage());
                addBooleanFilter(cb, root, predicates, "reafCollected", criteria.getReafCollected());


                // Relation Join
                if (criteria.getRealEstateAssestId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "realEstateAssest", "id", criteria.getRealEstateAssestId());
                }
                if (criteria.getReafCategoryId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reafCategory", "id", criteria.getReafCategoryId());
                }
                if (criteria.getReafCurrencyId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reafCurrency", "id", criteria.getReafCurrencyId());
                }
                if (criteria.getReafFrequencyId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reafFrequency", "id", criteria.getReafFrequencyId());
                }


            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
