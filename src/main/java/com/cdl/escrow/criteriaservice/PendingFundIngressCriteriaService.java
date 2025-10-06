package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.PendingFundIngressCriteria;
import com.cdl.escrow.dto.PendingFundIngressDTO;
import com.cdl.escrow.entity.*;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.PendingFundIngressMapper;
import com.cdl.escrow.repository.PendingFundIngressRepository;
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
public class PendingFundIngressCriteriaService extends BaseSpecificationBuilder<PendingFundIngress> implements Serializable {

    private final transient PendingFundIngressRepository pendingFundIngressRepository;

    private final transient PendingFundIngressMapper pendingFundIngressMapper;

    public Page<PendingFundIngressDTO> findByCriteria(PendingFundIngressCriteria criteria, Pageable pageable) {
        Specification<PendingFundIngress> specification = createSpecification(criteria);
        return pendingFundIngressRepository.findAll(specification, pageable).map(pendingFundIngressMapper::toDto);
    }

    public List<PendingFundIngressDTO> findByListCriteria(PendingFundIngressCriteria criteria) {
        Specification<PendingFundIngress> specification = createSpecification(criteria);
        List<PendingFundIngress> result = pendingFundIngressRepository.findAll(specification);
        return pendingFundIngressMapper.toDto(result);
    }

    private Specification<PendingFundIngress> createSpecification(PendingFundIngressCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "ptfiTransactionId", criteria.getPtfiTransactionId(), true);
                addStringFilter(cb,root,predicates,"ptfiTransactionRefId",criteria.getPtfiTransactionRefId(),true);
                addDoubleFilter(cb, root, predicates, "ptfiAmount", criteria.getPtfiAmount());
                addDoubleFilter(cb,root,predicates,"ptfiTotalAmount", criteria.getPtfiTotalAmount());
                addZonedDateTimeFilter(cb,root,predicates,"ptfiTransactionDate",criteria.getPtfiTransactionDate());
                addStringFilter(cb, root, predicates, "ptfiNarration", criteria.getPtfiNarration(), true);
                addStringFilter(cb, root, predicates, "ptfiDescription", criteria.getPtfiDescription(), true);
                addBooleanFilter(cb,root,predicates,"ptfiDiscard", criteria.getPtfiDiscard());
                addBooleanFilter(cb,root,predicates,"ptfiIsAllocated",criteria.getPtfiIsAllocated());
                addStringFilter(cb, root, predicates, "ptfiTransParticular1", criteria.getPtfiTransParticular1(), true);
                addStringFilter(cb, root, predicates, "ptfiTransParticular2", criteria.getPtfiTransParticular2(), true);
                addStringFilter(cb, root, predicates, "ptfiTransRemark1", criteria.getPtfiTransRemark1(), true);
                addStringFilter(cb, root, predicates, "ptfiTransRemark2", criteria.getPtfiTransRemark2(), true);
                addStringFilter(cb,root,predicates,"ptfiCheckNumber",criteria.getPtfiCheckNumber(),true);
                addBooleanFilter(cb,root,predicates,"ptfiTasUpdated", criteria.getPtfiTasUpdated());
                addBooleanFilter(cb,root,predicates,"ptfiTasUpdate",criteria.getPtfiTasUpdate());
                addStringFilter(cb,root,predicates,"ptfiUnitRefNumber",criteria.getPtfiUnitRefNumber(),true);
                addZonedDateTimeFilter(cb,root,predicates,"ptfiValueDateTime",criteria.getPtfiValueDateTime());
                addZonedDateTimeFilter(cb,root,predicates,"ptfiPostedDateTime",criteria.getPtfiPostedDateTime());
                addZonedDateTimeFilter(cb,root,predicates,"ptfiNormalDateTime",criteria.getPtfiNormalDateTime());
                addStringFilter(cb,root,predicates,"ptfiBranchCode",criteria.getPtfiBranchCode(),true);
                addStringFilter(cb,root,predicates,"ptfiPostedBranchCode",criteria.getPtfiPostedBranchCode(),true);
                addStringFilter(cb,root,predicates,"ptfiCurrencyCode",criteria.getPtfiCurrencyCode(),true);
                addStringFilter(cb,root,predicates,"ptfiSpecialField1",criteria.getPtfiSpecialField1(),true);
                addStringFilter(cb,root,predicates,"ptfiSpecialField2",criteria.getPtfiSpecialField2(),true);
                addStringFilter(cb,root,predicates,"ptfiSpecialField3",criteria.getPtfiSpecialField3(),true);
                addStringFilter(cb,root,predicates,"ptfiSpecialField4",criteria.getPtfiSpecialField4(),true);
                addStringFilter(cb,root,predicates,"ptfiSpecialField5",criteria.getPtfiSpecialField5(),true);

                addDoubleFilter(cb,root,predicates,"ptfiRetentionAmount",criteria.getPtfiRetentionAmount());
                addBooleanFilter(cb,root,predicates,"ptfiIsUnAllocatedCategory",criteria.getPtfiIsUnAllocatedCategory());
                addStringFilter(cb,root,predicates,"ptfiPrimaryUnitHolderName",criteria.getPtfiPrimaryUnitHolderName(),true);
                addStringFilter(cb,root,predicates,"pfiTasPaymentStatus",criteria.getPtfiTasPaymentStatus(),true);
                addZonedDateTimeFilter(cb,root,predicates,"ptfiDiscardedDateTime", criteria.getPtfiDiscardedDateTime());

                addBooleanFilter(cb,root,predicates,"ptfiCreditedEscrow",criteria.getPtfiCreditedEscrow());

                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

                addStringFilter(cb,root,predicates,"ptfiPaymentRefNo",criteria.getPtfiPaymentRefNo(),true);
                addStringFilter(cb,root,predicates,"ptfiCbsResponse",criteria.getPtfiCbsResponse(),true);


                //addLongFilter(cb, root, predicates, "realEstateAssestId", criteria.getRealEstateAssestId());
               // addLongFilter(cb, root, predicates, "capitalPartnerUnitId", criteria.getCapitalPartnerUnitId());
              //  addLongFilter(cb, root, predicates, "bucketTypeId", criteria.getBucketTypeId());
               // addLongFilter(cb, root, predicates, "bankAccountId", criteria.getBankAccountId());
              //  addLongFilter(cb, root, predicates, "depositModeId", criteria.getDepositModeId());
              //  addLongFilter(cb, root, predicates, "subDepositTypeId", criteria.getSubDepositTypeId());


                // relationships

                if (criteria.getRealEstateAssestId() != null) {
                    Join<PendingFundIngress, RealEstateAssest> join = root.join("realEstateAssestId", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getRealEstateAssestId());
                }

                if (criteria.getBucketTypeId() != null) {
                    Join<PendingFundIngress, ApplicationSetting> join = root.join("bucketTypeId", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getBucketTypeId());
                }
                if (criteria.getCapitalPartnerUnitId() != null) {
                    Join<PendingFundIngress, CapitalPartner> join = root.join("capitalPartnerUnitId", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getCapitalPartnerUnitId());
                }

                if (criteria.getDepositModeId() != null) {
                    Join<PendingFundIngress, ApplicationSetting> join = root.join("depositModeId", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getDepositModeId());
                }

                if (criteria.getSubDepositTypeId() != null) {
                    Join<PendingFundIngress, ApplicationSetting> join = root.join("subDepositTypeId", JoinType.LEFT);
                    addLongFilterOnJoin(cb, join, predicates, "id", criteria.getSubDepositTypeId());
                }

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
