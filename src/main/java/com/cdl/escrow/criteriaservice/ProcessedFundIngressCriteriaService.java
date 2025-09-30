package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.ProcessedFundIngressCriteria;
import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import com.cdl.escrow.entity.ProcessedFundIngress;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ProcessedFundIngressMapper;
import com.cdl.escrow.repository.ProcessedFundIngressRepository;
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
public class ProcessedFundIngressCriteriaService extends BaseSpecificationBuilder<ProcessedFundIngress> implements Serializable {

    private final transient ProcessedFundIngressRepository processedFundIngressRepository;

    private final transient ProcessedFundIngressMapper processedFundIngressMapper;

    public Page<ProcessedFundIngressDTO> findByCriteria(ProcessedFundIngressCriteria criteria, Pageable pageable) {
        Specification<ProcessedFundIngress> specification = createSpecification(criteria);
        return processedFundIngressRepository.findAll(specification, pageable).map(processedFundIngressMapper::toDto);
    }


    private Specification<ProcessedFundIngress> createSpecification(ProcessedFundIngressCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "pfiTransactionId", criteria.getPfiTransactionId(), true);
                addDoubleFilter(cb, root, predicates, "pfiAmount", criteria.getPfiAmount());
                addZonedDateTimeFilter(cb,root,predicates,"pfiTransactionDate",criteria.getPfiTransactionDate());
                addStringFilter(cb, root, predicates, "pfiNarration", criteria.getPfiNarration(), true);
                addStringFilter(cb, root, predicates, "pfiDescription", criteria.getPfiDescription(), true);
                addBooleanFilter(cb, root, predicates, "pfiUpdateTas", criteria.getPfiUpdateTas());
                addBooleanFilter(cb, root, predicates, "pfiTakenRetention", criteria.getPfiTakenRetention());
                addStringFilter(cb, root, predicates, "pfiRemark", criteria.getPfiRemark(), true);
                addBooleanFilter(cb,root,predicates,"pfiIsAllocated",criteria.getPfiIsAllocated());
                addStringFilter(cb, root, predicates, "pfiTransParticular1", criteria.getPfiTransParticular1(), true);
                addStringFilter(cb, root, predicates, "pfiTransParticular2", criteria.getPfiTransParticular2(), true);
                addStringFilter(cb, root, predicates, "pfiTransRemark1", criteria.getPfiTransRemark1(), true);
                addStringFilter(cb, root, predicates, "pfiTransRemark2", criteria.getPfiTransRemark2(), true);
                addStringFilter(cb,root,predicates,"pfiCheckNumber",criteria.getPfiCheckNumber(),true);
                addBooleanFilter(cb,root,predicates,"pfiTasUpdated", criteria.getPfiTasUpdated());
                addBooleanFilter(cb,root,predicates,"pfiTasUpdate",criteria.getPfiTasUpdate());
                addStringFilter(cb,root,predicates,"pfiUnitRefNumber",criteria.getPfiUnitRefNumber(),true);
                addDoubleFilter(cb,root,predicates,"pfiRetentionAmount",criteria.getPfiRetentionAmount());

                addStringFilter(cb,root,predicates,"pfiPrimaryUnitHolderName",criteria.getPfiPrimaryUnitHolderName(),true);
                addStringFilter(cb,root,predicates,"pfiTasPaymentStatus",criteria.getPfiTasPaymentStatus(),true);
                addStringFilter(cb,root,predicates,"pfiBatchTransId",criteria.getPfiBatchTransId(),true);
                addStringFilter(cb,root,predicates,"pfiReconResponse",criteria.getPfiReconResponse(),true);
                addStringFilter(cb,root,predicates,"pfiCbsResponse",criteria.getPfiCbsResponse(),true);
                addStringFilter(cb,root,predicates,"pfiPaymentRefNo",criteria.getPfiPaymentRefNo(),true);
                addStringFilter(cb,root,predicates,"pfiTasRefNo",criteria.getPfiTasRefNo(),true);
                addStringFilter(cb,root,predicates,"pfiSpecialField1",criteria.getPfiSpecialField1(),true);
                addStringFilter(cb,root,predicates,"pfiSpecialField2",criteria.getPfiSpecialField2(),true);

                addDoubleFilter(cb,root,predicates,"pfiTotalAmount",criteria.getPfiTotalAmount());
                addBooleanFilter(cb,root,predicates,"pfiIsRoleback", criteria.getPfiIsRoleback());
                addBooleanFilter(cb,root,predicates,"pfiCreditedRetention", criteria.getPfiCreditedRetention());

                addLongFilter(cb, root, predicates, "realEstateAssestId", criteria.getRealEstateAssestId());
                addLongFilter(cb, root, predicates, "capitalPartnerUnitId", criteria.getCapitalPartnerUnitId());
                addLongFilter(cb, root, predicates, "bucketTypeId", criteria.getBucketTypeId());
                addLongFilter(cb, root, predicates, "bucketSubTypeId", criteria.getBucketSubTypeId());
                addLongFilter(cb, root, predicates, "depositModeId", criteria.getDepositModeId());
                addLongFilter(cb, root, predicates, "subDepositTypeId", criteria.getSubDepositTypeId());
                addLongFilter(cb, root, predicates, "pendingFundIngressId", criteria.getPendingFundIngressId());

                addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
