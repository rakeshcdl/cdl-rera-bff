package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.BuildPartnerCriteria;
import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BuildPartnerMapper;
import com.cdl.escrow.repository.BuildPartnerRepository;
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
public class BuildPartnerCriteriaService extends BaseSpecificationBuilder<BuildPartner> implements Serializable {

    private final transient BuildPartnerRepository buildPartnerRepository;

    private final transient BuildPartnerMapper buildPartnerMapper;

    public Page<BuildPartnerDTO> findByCriteria(BuildPartnerCriteria criteria, Pageable pageable) {
        Specification<BuildPartner> specification = createSpecification(criteria);
        return buildPartnerRepository.findAll(specification, pageable).map(buildPartnerMapper::toDto);
    }

    public Specification<BuildPartner> createSpecification(BuildPartnerCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "bpDeveloperId", criteria.getBpDeveloperId(), true);
                addStringFilter(cb, root, predicates, "bpCifrera", criteria.getBpCifrera(), true);
                addStringFilter(cb, root, predicates, "bpDeveloperRegNo", criteria.getBpDeveloperRegNo(), true);
                addStringFilter(cb, root, predicates, "bpName", criteria.getBpName(), true);
                addStringFilter(cb, root, predicates, "bpMasterName", criteria.getBpMasterName(), true);
                addStringFilter(cb, root, predicates, "bpNameLocal", criteria.getBpNameLocal(), true);
                addZonedDateTimeFilter(cb, root, predicates, "bpOnboardingDate", criteria.getBpOnboardingDate());
                addStringFilter(cb, root, predicates, "bpContactAddress", criteria.getBpContactAddress(), true);
                addStringFilter(cb, root, predicates, "bpContactTel", criteria.getBpContactTel(), true);
                addStringFilter(cb, root, predicates, "bpPoBox", criteria.getBpPoBox(), true);
                addStringFilter(cb, root, predicates, "bpMobile", criteria.getBpMobile(), true);
                addStringFilter(cb, root, predicates, "bpFax", criteria.getBpFax(), true);
                addStringFilter(cb, root, predicates, "bpEmail", criteria.getBpEmail(), true);
                addStringFilter(cb, root, predicates, "bpLicenseNo", criteria.getBpLicenseNo(), true);
                addZonedDateTimeFilter(cb, root, predicates, "bpLicenseExpDate", criteria.getBpLicenseExpDate());
                addStringFilter(cb, root, predicates, "bpWorldCheckFlag", criteria.getBpWorldCheckFlag(), true);
                addStringFilter(cb, root, predicates, "bpWorldCheckRemarks", criteria.getBpWorldCheckRemarks(), true);
                addBooleanFilter(cb, root, predicates, "bpMigratedData", criteria.getBpMigratedData());
                addStringFilter(cb, root, predicates, "bpremark", criteria.getBpremark(), true);

                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());
                // relationships

                if (criteria.getBpRegulatorId() != null) {
                    Join<BuildPartner, ApplicationSetting> join = root.join("bpRegulator", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getBpRegulatorId());
                }

                if (criteria.getBpActiveStatusId() != null) {
                    Join<BuildPartner, ApplicationSetting> join = root.join("bpActiveStatus", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getBpActiveStatusId());
                }

            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
