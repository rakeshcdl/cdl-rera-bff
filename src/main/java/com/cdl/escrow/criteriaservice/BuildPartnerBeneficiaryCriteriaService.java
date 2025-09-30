package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.BuildPartnerBeneficiaryCriteria;
import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.BuildPartnerBeneficiary;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BuildPartnerBeneficiaryMapper;
import com.cdl.escrow.repository.BuildPartnerBeneficiaryRepository;
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
public class BuildPartnerBeneficiaryCriteriaService extends BaseSpecificationBuilder<BuildPartnerBeneficiary> implements Serializable {

    private final transient BuildPartnerBeneficiaryRepository buildPartnerBeneficiaryRepository;

    private final transient BuildPartnerBeneficiaryMapper buildPartnerBeneficiaryMapper;

    public Page<BuildPartnerBeneficiaryDTO> findByCriteria(BuildPartnerBeneficiaryCriteria criteria, Pageable pageable) {
        Specification<BuildPartnerBeneficiary> specification = createSpecification(criteria);
        return buildPartnerBeneficiaryRepository.findAll(specification, pageable).map(buildPartnerBeneficiaryMapper::toDto);
    }

    private Specification<BuildPartnerBeneficiary> createSpecification(BuildPartnerBeneficiaryCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "bpbBeneficiaryId", criteria.getBpbBeneficiaryId(), true);
                addStringFilter(cb, root, predicates, "bpbBeneficiaryType", criteria.getBpbBeneficiaryType(), true);
                addStringFilter(cb, root, predicates, "bpbName", criteria.getBpbName(), true);
                addBooleanFilter(cb, root, predicates, "bpbIsActive", criteria.getBpbIsActive());
                addBooleanFilter(cb, root, predicates, "bpbIsDeleted", criteria.getBpbIsDeleted());

                // Relation Join
                if (criteria.getBpbTranferTypeId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "bpbTransferType", "id", criteria.getBpbTranferTypeId());
                }
                // ✅ ManyToMany: buildPartners
               /* if (criteria.getBuildPartnerId() != null) {
                    addLongManyToManyFilterForJoin(
                            cb, root, query, predicates,
                            "buildPartners", "id",
                            criteria.getBuildPartnerId()
                    );
                }*/

                // Filter by CapitalPartner id -> join the capitalPartners collection
                if (criteria.getBuildPartnerId() != null) {
                    Join<BuildPartnerBeneficiary, BuildPartner> join = root.join("buildPartners", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getBuildPartnerId());
                }

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
