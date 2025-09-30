package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.RealEstateAssestClosureCriteria;
import com.cdl.escrow.dto.RealEstateAssestClosureDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestClosure;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateAssestClosureMapper;
import com.cdl.escrow.repository.RealEstateAssestClosureRepository;
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
public class RealEstateAssestClosureCriteriaService  extends BaseSpecificationBuilder<RealEstateAssestClosure> implements Serializable {

    private final transient RealEstateAssestClosureRepository realEstateAssestClosureRepository;

    private final transient RealEstateAssestClosureMapper realEstateAssestClosureMapper;

    public Page<RealEstateAssestClosureDTO> findByCriteria(RealEstateAssestClosureCriteria criteria, Pageable pageable) {
        Specification<RealEstateAssestClosure> specification = createSpecification(criteria);
        return realEstateAssestClosureRepository.findAll(specification, pageable).map(realEstateAssestClosureMapper::toDto);
    }

    private Specification<RealEstateAssestClosure> createSpecification(RealEstateAssestClosureCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addDoubleFilter(cb, root, predicates, "reacTotalIncomeFund", criteria.getReacTotalIncomeFund());
                addDoubleFilter(cb, root, predicates, "reacTotalPayment", criteria.getReacTotalPayment());
                addDoubleFilter(cb, root, predicates, "reacCheckGuranteeDoc", criteria.getReacCheckGuranteeDoc());
                //addLongFilter(cb, root, predicates, "realEstateAssestId", criteria.getRealEstateAssestId());
               // addLongFilter(cb, root, predicates, "reacDocumentId", criteria.getReacDocumentId());


                if (criteria.getRealEstateAssestId() != null) {
                    Join<RealEstateAssestClosure, RealEstateAssest> join = root.join("realEstateAssest", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getRealEstateAssestId());
                }

               /* if (criteria.getRealEstateAssestId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "realEstateAssest", "id", criteria.getRealEstateAssestId());
                }*/
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
