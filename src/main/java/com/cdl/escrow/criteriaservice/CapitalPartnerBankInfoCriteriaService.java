package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.CapitalPartnerBankInfoCriteria;
import com.cdl.escrow.dto.CapitalPartnerBankInfoDTO;
import com.cdl.escrow.entity.CapitalPartnerBankInfo;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerBankInfoMapper;
import com.cdl.escrow.repository.CapitalPartnerBankInfoRepository;
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
public class CapitalPartnerBankInfoCriteriaService extends BaseSpecificationBuilder<CapitalPartnerBankInfo> implements Serializable {

    private final transient CapitalPartnerBankInfoRepository capitalPartnerBankInfoRepository;

    private final transient CapitalPartnerBankInfoMapper capitalPartnerBankInfoMapper;

    public Page<CapitalPartnerBankInfoDTO> findByCriteria(CapitalPartnerBankInfoCriteria criteria, Pageable pageable) {
        Specification<CapitalPartnerBankInfo> specification = createSpecification(criteria);
        return capitalPartnerBankInfoRepository.findAll(specification, pageable).map(capitalPartnerBankInfoMapper::toDto);
    }

    public Specification<CapitalPartnerBankInfo> createSpecification(CapitalPartnerBankInfoCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "cpbiPayeeName", criteria.getCpbiPayeeName(), true);
                addStringFilter(cb, root, predicates, "cpbiPayeeAddress", criteria.getCpbiPayeeAddress(), true);
                addStringFilter(cb, root, predicates, "cpbiBankName", criteria.getCpbiBankName(), true);
                addStringFilter(cb, root, predicates, "cpbiBankAddress", criteria.getCpbiBankAddress(), true);
                addStringFilter(cb, root, predicates, "cpbiBicCode", criteria.getCpbiBicCode(), true);
                addStringFilter(cb, root, predicates, "cpbiBeneRoutingCode", criteria.getCpbiBeneRoutingCode(), true);
               // addLongFilter(cb, root, predicates, "bankAccountId", criteria.getBankAccountId());
                //addLongFilter(cb, root, predicates, "capitalPartnerId", criteria.getCapitalPartnerId());
                //addLongFilter(cb, root, predicates, "payModeId", criteria.getPayModeId());

                // Relation Join
                if (criteria.getCapitalPartnerId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "capitalPartner", "id", criteria.getCapitalPartnerId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
