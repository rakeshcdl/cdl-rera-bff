package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.LongFilter;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RealEstateAssestPaymentPlanCriteria {

    private LongFilter id;

    private LongFilter realEstateAssestId;
}
