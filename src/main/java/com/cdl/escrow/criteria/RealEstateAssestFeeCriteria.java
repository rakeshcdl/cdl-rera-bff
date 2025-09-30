package com.cdl.escrow.criteria;

import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.DoubleFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RealEstateAssestFeeCriteria implements Serializable {

    private LongFilter id;

    private DoubleFilter reafAmount;

    private DoubleFilter reafTotalAmount;

    private ZonedDateTimeFilter reafCalender;

    private ZonedDateTimeFilter reafNextRecoveryDate;

    private DoubleFilter reafVatPercentage;

    private BooleanFilter reafCollected;

    private LongFilter realEstateAssestId;

    private LongFilter reafCategoryId;

    private LongFilter reafCurrencyId;

    private LongFilter reafFrequencyId;

    //private Set<RealEstateAssestFeeHistoryDTO> realEstateAssestFeeHistoryDTOS ;
}
