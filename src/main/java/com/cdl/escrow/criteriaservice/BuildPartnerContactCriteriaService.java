package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.BuildPartnerContactCriteria;
import com.cdl.escrow.dto.BuildPartnerContactDTO;
import com.cdl.escrow.entity.BuildPartnerContact;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BuildPartnerContactMapper;
import com.cdl.escrow.repository.BuildPartnerContactRepository;
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
public class BuildPartnerContactCriteriaService extends BaseSpecificationBuilder<BuildPartnerContact> implements Serializable {

    private final transient BuildPartnerContactRepository buildPartnerContactRepository;

    private final transient BuildPartnerContactMapper buildPartnerContactMapper;

    public Page<BuildPartnerContactDTO> findByCriteria(BuildPartnerContactCriteria criteria, Pageable pageable) {
        Specification<BuildPartnerContact> specification = createSpecification(criteria);
        return buildPartnerContactRepository.findAll(specification, pageable).map(buildPartnerContactMapper::toDto);
    }

    public Specification<BuildPartnerContact> createSpecification(BuildPartnerContactCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "bpcContactName", criteria.getBpcContactName(), true);
                addStringFilter(cb, root, predicates, "bpcCcontactTelCode", criteria.getBpcCcontactTelCode(), false);
                addStringFilter(cb, root, predicates, "bpcCcontactTelNo", criteria.getBpcCcontactTelNo(), false);
                addStringFilter(cb, root, predicates, "bpcCcountryMobCode", criteria.getBpcCcountryMobCode(), false);
                addStringFilter(cb, root, predicates, "bpcCcontactMobNo", criteria.getBpcCcontactMobNo(), false);
                addStringFilter(cb, root, predicates, "bpcCcontactEmail", criteria.getBpcCcontactEmail(), true);
                addStringFilter(cb, root, predicates, "bpcCcontactAddress", criteria.getBpcCcontactAddress(), true);
                addStringFilter(cb, root, predicates, "bpcCcontactPoBox", criteria.getBpcCcontactPoBox(), false);
                addStringFilter(cb, root, predicates, "bpcCcontactFaxNo", criteria.getBpcCcontactFaxNo(), false);

                // Enum or direct equals
                if (criteria.getWorkflowStatus() != null) {
                    predicates.add(cb.equal(root.get("workflowStatus"), criteria.getWorkflowStatus()));
                }

                // Relation Join
                if (criteria.getBuildPartnerId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "buildPartner", "id", criteria.getBuildPartnerId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
