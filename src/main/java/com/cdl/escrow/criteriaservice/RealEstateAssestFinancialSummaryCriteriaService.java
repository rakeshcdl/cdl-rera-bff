package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.RealEstateAssestFinancialSummaryCriteria;
import com.cdl.escrow.dto.RealEstateAssestFinancialSummaryDTO;
import com.cdl.escrow.entity.RealEstateAssestFinancialSummary;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateAssestFinancialSummaryMapper;
import com.cdl.escrow.repository.RealEstateAssestFinancialSummaryRepository;
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
public class RealEstateAssestFinancialSummaryCriteriaService extends BaseSpecificationBuilder<RealEstateAssestFinancialSummary> implements Serializable {

    private final transient RealEstateAssestFinancialSummaryRepository realEstateAssestFinancialSummaryRepository;

    private final transient RealEstateAssestFinancialSummaryMapper realEstateAssestFinancialSummaryMapper;

    public Page<RealEstateAssestFinancialSummaryDTO> findByCriteria(RealEstateAssestFinancialSummaryCriteria criteria, Pageable pageable) {
        Specification<RealEstateAssestFinancialSummary> specification = createSpecification(criteria);
        return realEstateAssestFinancialSummaryRepository.findAll(specification, pageable).map(realEstateAssestFinancialSummaryMapper::toDto);
    }

    private Specification<RealEstateAssestFinancialSummary> createSpecification(RealEstateAssestFinancialSummaryCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "reafsEstRevenue", criteria.getReafsEstRevenue(), true);
                addDoubleFilter(cb, root, predicates, "reafsEstConstructionCost", criteria.getReafsActualConstructionCost());
                addDoubleFilter(cb, root, predicates, "reafsEstProjectMgmtExpense", criteria.getReafsActualProjectMgmtExpense());
                addDoubleFilter(cb, root, predicates, "reafsEstLandCost", criteria.getReafsEstLandCost());
                addDoubleFilter(cb, root, predicates, "reafsEstMarketingExpense", criteria.getReafsEstMarketingExpense());
                addZonedDateTimeFilter(cb, root, predicates, "reafsEstimatedDate", criteria.getReafsEstimatedDate());
                addStringFilter(cb, root, predicates, "reafsEstExceptionalCapVal", criteria.getReafsEstExceptionalCapVal(), true);
                addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", criteria.getReafsActualSoldValue());
                addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", criteria.getReafsActualConstructionCost());
                addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", criteria.getReafsActualInfraCost());
                addDoubleFilter(cb, root, predicates, "reafsActualLandCost", criteria.getReafsActualLandCost());
                addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", criteria.getReafsActualMarketingExp());
                addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", criteria.getReafsActualProjectMgmtExpense());
                addZonedDateTimeFilter(cb, root, predicates, "reafsActualDate", criteria.getReafsActualDate());
                addStringFilter(cb, root, predicates, "reafsActualexceptCapVal", criteria.getReafsActualexceptCapVal(), true);

                if (criteria.getRealEstateAssestId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "realEstateAssest", "id", criteria.getRealEstateAssestId());
                }

           /* addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);

            addDoubleFilter(cb, root, predicates, "reafsActualSoldValue", this.reafsActualSoldValue);
            addDoubleFilter(cb, root, predicates, "reafsActualConstructionCost", this.reafsActualConstructionCost);
            addDoubleFilter(cb, root, predicates, "reafsActualInfraCost", this.reafsActualInfraCost);
            addDoubleFilter(cb, root, predicates, "reafsActualLandCost", this.reafsActualLandCost);
            addDoubleFilter(cb, root, predicates, "reafsActualMarketingExp", this.reafsActualMarketingExp);
            addDoubleFilter(cb, root, predicates, "reafsActualProjectMgmtExpense", this.reafsActualProjectMgmtExpense);*/
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
