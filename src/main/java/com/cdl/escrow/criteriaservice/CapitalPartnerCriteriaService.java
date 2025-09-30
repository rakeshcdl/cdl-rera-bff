package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.CapitalPartnerCriteria;
import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerMapper;
import com.cdl.escrow.repository.CapitalPartnerRepository;
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
public class CapitalPartnerCriteriaService extends BaseSpecificationBuilder<CapitalPartner> implements Serializable {

    private final transient CapitalPartnerRepository capitalPartnerRepository;

    private final transient CapitalPartnerMapper capitalPartnerMapper;

    public Page<CapitalPartnerDTO> findByCriteria(CapitalPartnerCriteria criteria, Pageable pageable) {
        Specification<CapitalPartner> specification = createSpecification(criteria);
        return capitalPartnerRepository.findAll(specification, pageable).map(capitalPartnerMapper::toDto);
    }

    public Specification<CapitalPartner> createSpecification(CapitalPartnerCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long Filters
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addLongFilter(cb, root, predicates, "documentTypeId", criteria.getDocumentTypeId());
                addLongFilter(cb, root, predicates, "countryOptionId", criteria.getCountryOptionId());
                addLongFilter(cb, root, predicates, "investorTypeId", criteria.getInvestorTypeId());
                addLongFilter(cb, root, predicates, "capitalPartnerUnitId", criteria.getCapitalPartnerUnitId());

                // String Filters
                addStringFilter(cb, root, predicates, "capitalPartnerId", criteria.getCapitalPartnerId(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerName", criteria.getCapitalPartnerName(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerMiddleName", criteria.getCapitalPartnerMiddleName(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerLastName", criteria.getCapitalPartnerLastName(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerIdNo", criteria.getCapitalPartnerIdNo(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerTelephoneNo", criteria.getCapitalPartnerTelephoneNo(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerMobileNo", criteria.getCapitalPartnerMobileNo(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerEmail", criteria.getCapitalPartnerEmail(), true);
                addStringFilter(cb, root, predicates, "capitalPartnerLocaleName", criteria.getCapitalPartnerLocaleName(), true);

                // Float Filter
                addFloatFilter(cb, root, predicates, "capitalPartnerOwnershipPercentage", criteria.getCapitalPartnerOwnershipPercentage());

                // Integer Filter
                addIntegerFilter(cb, root, predicates, "capitalPartnerOwnerNumber", criteria.getCapitalPartnerOwnerNumber());

                // Boolean Filter
                addBooleanFilter(cb, root, predicates, "isCurrent", criteria.getIsCurrent());

                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

                // ZonedDateTime Filter
                addZonedDateTimeFilter(cb, root, predicates, "idExpiaryDate", criteria.getIdExpiaryDate());
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
