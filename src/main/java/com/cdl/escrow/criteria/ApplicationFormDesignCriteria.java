package com.cdl.escrow.criteria;

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
public class ApplicationFormDesignCriteria implements Serializable {

    private LongFilter id;

    private StringFilter formName;

    private StringFilter formDefinition;
}
