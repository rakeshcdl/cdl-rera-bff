package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.CapitalPartnerUnitCriteria;
import com.cdl.escrow.dto.CapitalPartnerUnitDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerUnitMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitRepository;
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
public class CapitalPartnerUnitCriteriaService extends BaseSpecificationBuilder<CapitalPartnerUnit> implements Serializable {

    private final transient CapitalPartnerUnitRepository capitalPartnerUnitRepository;

    private final transient CapitalPartnerUnitMapper capitalPartnerUnitMapper;

    public Page<CapitalPartnerUnitDTO> findByCriteria(CapitalPartnerUnitCriteria criteria, Pageable pageable) {
        Specification<CapitalPartnerUnit> specification = createSpecification(criteria);
        return capitalPartnerUnitRepository.findAll(specification, pageable).map(capitalPartnerUnitMapper::toDto);
    }


    public Specification<CapitalPartnerUnit> createSpecification(CapitalPartnerUnitCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long filters
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addLongFilter(cb, root, predicates, "partnerUnitId", criteria.getPartnerUnitId());
                addLongFilter(cb, root, predicates, "capitalPartnerUnitTypeId", criteria.getCapitalPartnerUnitTypeId());
                addLongFilter(cb, root, predicates, "realEstateAssestId", criteria.getRealEstateAssestId());
                addLongFilter(cb, root, predicates, "unitStatusId", criteria.getUnitStatusId());
                addLongFilter(cb, root, predicates, "propertyId", criteria.getPropertyId());
                addLongFilter(cb, root, predicates, "paymentPlanTypeId", criteria.getPaymentPlanTypeId());
                addLongFilter(cb, root, predicates, "capitalPartnerUnitBookingId", criteria.getCapitalPartnerUnitBookingId());

                // String filters
                addStringFilter(cb, root, predicates, "unitRefId", criteria.getUnitRefId(), true);
                addStringFilter(cb, root, predicates, "altUnitRefId", criteria.getAltUnitRefId(), true);
                addStringFilter(cb, root, predicates, "name", criteria.getName(), true);
                addStringFilter(cb, root, predicates, "unitSysId", criteria.getUnitSysId(), true);
                addStringFilter(cb, root, predicates, "otherFormatUnitNo", criteria.getOtherFormatUnitNo(), true);
                addStringFilter(cb, root, predicates, "virtualAccNo", criteria.getVirtualAccNo(), true);
                addStringFilter(cb, root, predicates, "towerName", criteria.getTowerName(), true);
                addStringFilter(cb, root, predicates, "unitPlotSize", criteria.getUnitPlotSize(), true);
                addStringFilter(cb, root, predicates, "floor", criteria.getFloor(), true);
                addStringFilter(cb, root, predicates, "noofBedroom", criteria.getNoofBedroom(), true);

                // Boolean filters
                addBooleanFilter(cb, root, predicates, "isResale", criteria.getIsResale());
                addBooleanFilter(cb, root, predicates, "isModified", criteria.getIsModified());

                // ZonedDateTime filters
                addZonedDateTimeFilter(cb, root, predicates, "resaleDate", criteria.getResaleDate());

                // Filter by CapitalPartner id -> join the capitalPartners collection
                if (criteria.getCapitalPartnerId() != null) {
                    Join<CapitalPartnerUnit, CapitalPartner> join = root.join("capitalPartners", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getCapitalPartnerId());
                }

                if (criteria.getCapitalPartnerUnitTypeId() != null) {
                    Join<CapitalPartnerUnit, ApplicationSetting> join = root.join("capitalPartnerUnitType", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getCapitalPartnerUnitTypeId());
                }

                if (criteria.getUnitStatusId() != null) {
                    Join<CapitalPartnerUnit, ApplicationSetting> join = root.join("unitStatus", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getUnitStatusId());
                }

                if (criteria.getPropertyId() != null) {
                    Join<CapitalPartnerUnit, ApplicationSetting> join = root.join("propertyId", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getPropertyId());
                }

                if (criteria.getPaymentPlanTypeId() != null) {
                    Join<CapitalPartnerUnit, ApplicationSetting> join = root.join("paymentPlanType", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getPaymentPlanTypeId());
                }

                if (criteria.getCapitalPartnerUnitBookingId() != null) {
                    Join<CapitalPartnerUnit, ApplicationSetting> join = root.join("capitalPartnerUnitBooking", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getCapitalPartnerUnitBookingId());
                }

            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
