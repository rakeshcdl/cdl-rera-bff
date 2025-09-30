package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.CapitalPartnerUnitBookingCriteria;
import com.cdl.escrow.dto.CapitalPartnerUnitBookingDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.entity.CapitalPartnerUnitBooking;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerUnitBookingMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitBookingRepository;
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
public class CapitalPartnerUnitBookingCriteriaService extends BaseSpecificationBuilder<CapitalPartnerUnitBooking> implements Serializable {

    private final transient CapitalPartnerUnitBookingRepository capitalPartnerUnitBookingRepository;

    private final transient CapitalPartnerUnitBookingMapper capitalPartnerUnitBookingMapper;

    public Page<CapitalPartnerUnitBookingDTO> findByCriteria(CapitalPartnerUnitBookingCriteria criteria, Pageable pageable) {
        Specification<CapitalPartnerUnitBooking> specification = createSpecification(criteria);
        return capitalPartnerUnitBookingRepository.findAll(specification, pageable).map(capitalPartnerUnitBookingMapper::toDto);
    }


    public Specification<CapitalPartnerUnitBooking> createSpecification(CapitalPartnerUnitBookingCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long filters
                addLongFilter(cb, root, predicates, "id", criteria.getId());

                // Double filters
                addDoubleFilter(cb, root, predicates, "cpubAmountPaid", criteria.getCpubAmountPaid());
                addDoubleFilter(cb, root, predicates, "cpubAreaSize", criteria.getCpubAreaSize());
                addDoubleFilter(cb, root, predicates, "cpubForFeitAmount", criteria.getCpubForFeitAmount());
                addDoubleFilter(cb, root, predicates, "cpubDldAmount", criteria.getCpubDldAmount());
                addDoubleFilter(cb, root, predicates, "cpubRefundAmount", criteria.getCpubRefundAmount());
                addDoubleFilter(cb, root, predicates, "cpubTransferredAmount", criteria.getCpubTransferredAmount());

                // String filters
                addStringFilter(cb, root, predicates, "cpubRemarks", criteria.getCpubRemarks(), true);

                // Filter by CapitalPartner id -> join the capitalPartners collection
                if (criteria.getCapitalPartnerUnitId() != null) {
                    Join<CapitalPartnerUnitBooking, CapitalPartnerUnit> join = root.join("capitalPartnerUnits", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getCapitalPartnerUnitId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
