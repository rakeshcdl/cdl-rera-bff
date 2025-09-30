package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.CapitalPartnerUnitTypeCriteria;
import com.cdl.escrow.dto.CapitalPartnerUnitTypeDTO;
import com.cdl.escrow.entity.CapitalPartnerUnitType;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerUnitTypeMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitTypeRepository;
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
public class CapitalPartnerUnitTypeCriteriaService extends BaseSpecificationBuilder<CapitalPartnerUnitType>  implements Serializable {

    private final transient CapitalPartnerUnitTypeRepository capitalPartnerUnitTypeRepository;

    private final transient CapitalPartnerUnitTypeMapper capitalPartnerUnitTypeMapper;

    public Page<CapitalPartnerUnitTypeDTO> findByCriteria(CapitalPartnerUnitTypeCriteria criteria, Pageable pageable) {
        Specification<CapitalPartnerUnitType> specification = createSpecification(criteria);
        return capitalPartnerUnitTypeRepository.findAll(specification, pageable).map(capitalPartnerUnitTypeMapper::toDto);
    }


    public Specification<CapitalPartnerUnitType> createSpecification(CapitalPartnerUnitTypeCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long Filters
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addLongFilter(cb, root, predicates, "capitalPartnerParentUnitTypeId", criteria.getCapitalPartnerParentUnitTypeId());

                // String Filters
                addStringFilter(cb, root, predicates, "cputName", criteria.getCputName(), true);
                addStringFilter(cb, root, predicates, "cputIconContentType", criteria.getCputIconContentType(), true);
                addStringFilter(cb, root, predicates, "cpUnitTypePrefix", criteria.getCpUnitTypePrefix(), true);
                addStringFilter(cb, root, predicates, "cputExcelFormula", criteria.getCputExcelFormula(), true);
                addStringFilter(cb, root, predicates, "cputJsFormula", criteria.getCputJsFormula(), true);

                // Boolean Filters
                addBooleanFilter(cb, root, predicates, "cputIsStandalone", criteria.getCputIsStandalone());
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
