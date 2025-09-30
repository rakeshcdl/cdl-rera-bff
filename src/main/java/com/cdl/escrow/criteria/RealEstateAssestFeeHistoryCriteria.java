package com.cdl.escrow.criteria;


import com.cdl.escrow.filter.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RealEstateAssestFeeHistoryCriteria implements Serializable {

    private LongFilter id;

    private DoubleFilter reafhAmount;

    private DoubleFilter reafhTotalAmount;

    private DoubleFilter reafhVatPercentage;

    private ZonedDateTimeFilter reafhTransactionDate;

    private BooleanFilter reafhSuccess;

    private BooleanFilter reafhStatus;

    private StringFilter reahfRemark;

    private StringFilter reafhFeeResponse;

    private StringFilter reafhResponseStatus;

    private StringFilter reafhSpecialField1;

    private StringFilter reafhSpecialField2;

    private StringFilter reafhSpecialField3;

    private StringFilter reafhSpecialField4;

    private StringFilter reafhSpecialField5;

    private StringFilter reafhFeeRequestBody;

    private LongFilter realEstateAssestFeeId;

    private LongFilter realEstateAssestId;

    private LongFilter capitalPartnerUnitId;

    private LongFilter fundEgressId;
}
