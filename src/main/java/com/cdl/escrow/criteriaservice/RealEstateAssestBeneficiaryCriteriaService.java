package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.RealEstateAssestBeneficiaryCriteria;
import com.cdl.escrow.dto.RealEstateAssestBeneficiaryDTO;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestBeneficiary;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateAssestBeneficiaryMapper;
import com.cdl.escrow.repository.RealEstateAssestBeneficiaryRepository;
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
public class RealEstateAssestBeneficiaryCriteriaService  extends BaseSpecificationBuilder<RealEstateAssestBeneficiary>  implements Serializable {

    private final transient RealEstateAssestBeneficiaryRepository realEstateAssestBeneficiaryRepository;

    private final transient RealEstateAssestBeneficiaryMapper realEstateAssestBeneficiaryMapper;

    public Page<RealEstateAssestBeneficiaryDTO> findByCriteria(RealEstateAssestBeneficiaryCriteria criteria, Pageable pageable) {
        Specification<RealEstateAssestBeneficiary> specification = createSpecification(criteria);
        return realEstateAssestBeneficiaryRepository.findAll(specification, pageable).map(realEstateAssestBeneficiaryMapper::toDto);
    }

    private Specification<RealEstateAssestBeneficiary> createSpecification(RealEstateAssestBeneficiaryCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "reabBeneficiaryId", criteria.getReabBeneficiaryId(), true);
                addStringFilter(cb, root, predicates, "reabName", criteria.getReabName(), true);
                addDoubleFilter(cb, root, predicates, "reabContractAmount", criteria.getReabContractAmount());
                addDoubleFilter(cb, root, predicates, "reabActualLandPrice", criteria.getReabActualLandPrice());
                addStringFilter(cb, root, predicates, "reabContractorName", criteria.getReabContractorName(), true);
                addStringFilter(cb, root, predicates, "reabType", criteria.getReabType(), true);
                addStringFilter(cb, root, predicates, "reabBank", criteria.getReabBank(), true);
                addStringFilter(cb, root, predicates, "reabSwift", criteria.getReabSwift(), true);
                addStringFilter(cb, root, predicates, "reabRoutingCode", criteria.getReabRoutingCode(), true);
                addStringFilter(cb, root, predicates, "reabAddress", criteria.getReabAddress(), true);
                addStringFilter(cb, root, predicates, "reabBankAddress", criteria.getReabBankAddress(), true);
                addBooleanFilter(cb, root, predicates, "reabIsActive", criteria.getReabIsActive());
                addBooleanFilter(cb, root, predicates, "reabIsDeleted", criteria.getReabIsDeleted());
                addLongFilter(cb, root, predicates, "reabTranferTypeId", criteria.getReabTranferTypeId());
                addLongFilter(cb, root, predicates, "reabExpenseTypeId", criteria.getReabExpenseTypeId());
                addLongFilter(cb, root, predicates, "reabVendorSubTypeId", criteria.getReabVendorSubTypeId());
                addLongFilter(cb, root, predicates, "reabContractorSubTypeId", criteria.getReabContractorSubTypeId());
                addLongFilter(cb, root, predicates, "reabInfrastructureCategoryId", criteria.getReabInfrastructureCategoryId());
                addLongFilter(cb, root, predicates, "reabSalesCategoryId", criteria.getReabSalesCategoryId());


                // Filter by CapitalPartner id -> join the capitalPartners collection
                if (criteria.getRealEstateAssestId() != null) {
                    Join<RealEstateAssestBeneficiary, RealEstateAssest> join = root.join("realEstateAssests", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getRealEstateAssestId());
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
