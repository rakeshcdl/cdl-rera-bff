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
public class ApplicationModuleCriteria implements Serializable {
    private LongFilter id ;

    private StringFilter moduleName;

    private StringFilter moduleDescription;

    private BooleanFilter isActive;
}
