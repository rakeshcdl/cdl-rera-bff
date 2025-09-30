package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.SuretyBondTypeCriteria;
import com.cdl.escrow.dto.SuretyBondTypeDTO;
import com.cdl.escrow.entity.SuretyBondType;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.SuretyBondTypeMapper;
import com.cdl.escrow.repository.SuretyBondTypeRepository;
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
public class SuretyBondTypeCriteriaService  extends BaseSpecificationBuilder<SuretyBondType> implements Serializable {

    private final transient SuretyBondTypeRepository suretyBondTypeRepository;

    private final transient SuretyBondTypeMapper suretyBondTypeMapper;

    public Page<SuretyBondTypeDTO> findByCriteria(SuretyBondTypeCriteria criteria, Pageable pageable) {
        Specification<SuretyBondType> specification = createSpecification(criteria);
        return suretyBondTypeRepository.findAll(specification, pageable).map(suretyBondTypeMapper::toDto);
    }

    private Specification<SuretyBondType> createSpecification(SuretyBondTypeCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "suretyBondTypeLabelId", criteria.getSuretyBondTypeLabelId(), true);
                addStringFilter(cb, root, predicates, "suretyBondTypeId", criteria.getSuretyBondTypeId(), true);
                addStringFilter(cb, root, predicates, "suretyBondTypeFetchUrl", criteria.getSuretyBondTypeFetchUrl(), true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
