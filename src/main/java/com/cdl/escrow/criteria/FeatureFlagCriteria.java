package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FeatureFlagCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter featureName;

    private StringFilter featureDescription;

    private BooleanFilter isEnabled;
}
