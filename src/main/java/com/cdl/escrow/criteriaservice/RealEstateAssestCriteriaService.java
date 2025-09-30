package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.RealEstateAssestCriteria;
import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateAssestMapper;
import com.cdl.escrow.repository.RealEstateAssestRepository;
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
public class RealEstateAssestCriteriaService extends BaseSpecificationBuilder<RealEstateAssest> implements Serializable {

    private final transient RealEstateAssestRepository realEstateAssestRepository;

    private final transient RealEstateAssestMapper realEstateAssestMapper;

    public Page<RealEstateAssestDTO> findByCriteria(RealEstateAssestCriteria criteria, Pageable pageable) {
        Specification<RealEstateAssest> specification = createSpecification(criteria);
        return realEstateAssestRepository.findAll(specification, pageable).map(realEstateAssestMapper::toDto);
    }

    private Specification<RealEstateAssest> createSpecification(RealEstateAssestCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "reaId", criteria.getReaId(), true);
                addStringFilter(cb, root, predicates, "reaCif", criteria.getReaCif(), true);
                addStringFilter(cb, root, predicates, "reaName", criteria.getReaName(), true);
                addStringFilter(cb, root, predicates, "reaNameLocal", criteria.getReaNameLocal(), true);
                addStringFilter(cb, root, predicates, "reaLocation", criteria.getReaLocation(), true);
                addStringFilter(cb, root, predicates, "reaReraNumber", criteria.getReaReraNumber(), true);
                addZonedDateTimeFilter(cb, root, predicates, "reaStartDate", criteria.getReaStartDate());
                addZonedDateTimeFilter(cb, root, predicates, "reaCompletionDate", criteria.getReaCompletionDate());
                addStringFilter(cb, root, predicates, "reaPercentComplete", criteria.getReaPercentComplete(), true);
                addDoubleFilter(cb, root, predicates, "reaConstructionCost", criteria.getReaConstructionCost());
                addZonedDateTimeFilter(cb, root, predicates, "reaAccStatusDate", criteria.getReaAccStatusDate());
                addZonedDateTimeFilter(cb, root, predicates, "reaRegistrationDate", criteria.getReaRegistrationDate());
                addIntegerFilter(cb, root, predicates, "reaNoOfUnits", criteria.getReaNoOfUnits());
                addStringFilter(cb, root, predicates, "reaRemarks", criteria.getReaRemarks(), true);
                addStringFilter(cb, root, predicates, "reaSpecialApproval", criteria.getReaSpecialApproval(), true);
                addStringFilter(cb, root, predicates, "reaManagedBy", criteria.getReaManagedBy(), true);
                addStringFilter(cb, root, predicates, "reaBackupUser", criteria.getReaBackupUser(), true);
                addStringFilter(cb, root, predicates, "reaRetentionPercent", criteria.getReaRetentionPercent(), true);
                addStringFilter(cb, root, predicates, "reaAdditionalRetentionPercent", criteria.getReaAdditionalRetentionPercent(), true);
                addStringFilter(cb, root, predicates, "reaTotalRetentionPercent", criteria.getReaTotalRetentionPercent(), true);
                addZonedDateTimeFilter(cb, root, predicates, "reaRetentionEffectiveDate", criteria.getReaRetentionEffectiveDate());
                addStringFilter(cb, root, predicates, "reaManagementExpenses", criteria.getReaManagementExpenses(), true);
                addStringFilter(cb, root, predicates, "reaMarketingExpenses", criteria.getReaMarketingExpenses(), true);
                addZonedDateTimeFilter(cb, root, predicates, "reaAccoutStatusDate", criteria.getReaAccoutStatusDate());
                addStringFilter(cb, root, predicates, "reaTeamLeadName", criteria.getReaTeamLeadName(), true);
                addStringFilter(cb, root, predicates, "reaRelationshipManagerName", criteria.getReaRelationshipManagerName(), true);
                addStringFilter(cb, root, predicates, "reaAssestRelshipManagerName", criteria.getReaAssestRelshipManagerName(), true);
                addDoubleFilter(cb, root, predicates, "reaRealEstateBrokerExp", criteria.getReaRealEstateBrokerExp());
                addDoubleFilter(cb, root, predicates, "reaAdvertisementExp", criteria.getReaAdvertisementExp());
                addStringFilter(cb, root, predicates, "reaLandOwnerName", criteria.getReaLandOwnerName(), true);
              //  addLongFilter(cb, root, predicates, "buildPartnerId", criteria.getBuildPartnerId());
              //  addLongFilter(cb, root, predicates, "reaStatusId", criteria.getReaStatusId());
             //   addLongFilter(cb, root, predicates, "reaTypeId", criteria.getReaTypeId());
             //   addLongFilter(cb, root, predicates, "reaAccountStatusId", criteria.getReaAccountStatusId());
              //  addLongFilter(cb, root, predicates, "reaConstructionCostCurrencyId", criteria.getReaConstructionCostCurrencyId());


                // relationships
                //realEstateAssest





                if (criteria.getBuildPartnerId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "buildPartner", "id", criteria.getBuildPartnerId());
                }

                if (criteria.getReaStatusId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reaStatus", "id", criteria.getReaStatusId());
                }

                if (criteria.getReaTypeId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reaType", "id", criteria.getReaTypeId());
                }

                if (criteria.getReaAccountStatusId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reaAccountStatus", "id", criteria.getReaAccountStatusId());
                }

                if (criteria.getReaConstructionCostCurrencyId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "reaConstructionCostCurrency", "id", criteria.getReaConstructionCostCurrencyId());
                }

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
