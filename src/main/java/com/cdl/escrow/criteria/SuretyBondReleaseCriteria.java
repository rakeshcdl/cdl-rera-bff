package com.cdl.escrow.criteria;

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
public class SuretyBondReleaseCriteria implements Serializable {
    private LongFilter id ;

    private ZonedDateTimeFilter suretyBondReleaseRequestDate;

    private LongFilter suretyBondId;
}
