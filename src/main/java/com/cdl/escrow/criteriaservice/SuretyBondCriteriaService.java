package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.SuretyBondCriteria;
import com.cdl.escrow.dto.SuretyBondDTO;
import com.cdl.escrow.entity.SuretyBond;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.SuretyBondMapper;
import com.cdl.escrow.repository.SuretyBondRepository;
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
public class SuretyBondCriteriaService extends BaseSpecificationBuilder<SuretyBond> implements Serializable {

    private final transient SuretyBondRepository suretyBondRepository;

    private final transient SuretyBondMapper suretyBondMapper;

    public Page<SuretyBondDTO> findByCriteria(SuretyBondCriteria criteria, Pageable pageable) {
        Specification<SuretyBond> specification = createSpecification(criteria);
        return suretyBondRepository.findAll(specification, pageable).map(suretyBondMapper::toDto);
    }

    private Specification<SuretyBond> createSpecification(SuretyBondCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "suretyBondReferenceNumber", criteria.getSuretyBondReferenceNumber(), true);
                addZonedDateTimeFilter(cb, root, predicates, "suretyBondDate", criteria.getSuretyBondDate());
                addStringFilter(cb, root, predicates, "suretyBondName", criteria.getSuretyBondName(), true);
                addBooleanFilter(cb, root, predicates, "suretyBondOpenEnded", criteria.getSuretyBondOpenEnded());
                addZonedDateTimeFilter(cb, root, predicates, "suretyBondExpirationDate", criteria.getSuretyBondExpirationDate());
                addDoubleFilter(cb, root, predicates, "suretyBondAmount", criteria.getSuretyBondAmount());
                addZonedDateTimeFilter(cb, root, predicates, "suretyBondProjectCompletionDate", criteria.getSuretyBondProjectCompletionDate());
                addStringFilter(cb, root, predicates, "suretyBondNoOfAmendment", criteria.getSuretyBondNoOfAmendment(), true);
                addStringFilter(cb, root, predicates, "suretyBondContractor", criteria.getSuretyBondContractor(), true);
                addLongFilter(cb, root, predicates, "suretyBondTypeId", criteria.getSuretyBondTypeId());
                addLongFilter(cb, root, predicates, "realEstateAssestId", criteria.getRealEstateAssestId());
                addLongFilter(cb, root, predicates, "issuerBankId", criteria.getIssuerBankId());
                addLongFilter(cb, root, predicates, "accountNumberId", criteria.getAccountNumberId());
                addLongFilter(cb, root, predicates, "suretyBondStatusId", criteria.getSuretyBondStatusId());

                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
