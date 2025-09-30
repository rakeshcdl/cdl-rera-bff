package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.BuildPartnerAccountCriteria;
import com.cdl.escrow.dto.BuildPartnerAccountDTO;
import com.cdl.escrow.entity.BuildPartnerAccount;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BuildPartnerAccountMapper;
import com.cdl.escrow.repository.BuildPartnerAccountRepository;
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
public class BuildPartnerAccountCriteriaService extends BaseSpecificationBuilder<BuildPartnerAccount> implements Serializable {

    private final transient BuildPartnerAccountRepository buildPartnerAccountRepository;

    private final transient BuildPartnerAccountMapper buildPartnerAccountMapper;

    public Page<BuildPartnerAccountDTO> findByCriteria(BuildPartnerAccountCriteria criteria, Pageable pageable) {
        Specification<BuildPartnerAccount> specification = createSpecification(criteria);
        return buildPartnerAccountRepository.findAll(specification, pageable).map(buildPartnerAccountMapper::toDto);
    }

    private Specification<BuildPartnerAccount> createSpecification(BuildPartnerAccountCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "accountType", criteria.getAccountType(), true);
                addStringFilter(cb, root, predicates, "accountNumber", criteria.getAccountNumber(), true);
                addStringFilter(cb, root, predicates, "ibanNumber", criteria.getIbanNumber(), true);
                addZonedDateTimeFilter(cb, root, predicates, "dateOpened", criteria.getDateOpened());
                addStringFilter(cb, root, predicates, "accountTitle", criteria.getAccountTitle(), true);
                addStringFilter(cb, root, predicates, "currencyCode", criteria.getCurrencyCode(), true);
                addBooleanFilter(cb, root, predicates, "isValidated", criteria.getIsValidated());
                addZonedDateTimeFilter(cb, root, predicates, "createdAt", criteria.getCreatedAt());
                addZonedDateTimeFilter(cb, root, predicates, "updatedAt", criteria.getUpdatedAt());

                // WorkflowStatus enum
                if (criteria.getStatus() != null) {
                    predicates.add(cb.equal(root.get("workflowStatus"), criteria.getStatus()));
                }

                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

                // Enum or direct equals


                // Relation Join
                if (criteria.getBuildPartnerId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "buildPartner", "id", criteria.getBuildPartnerId());
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
