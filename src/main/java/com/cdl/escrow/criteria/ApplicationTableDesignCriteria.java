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
public class ApplicationTableDesignCriteria implements Serializable {
    private LongFilter id;

    private StringFilter tableName;

    private StringFilter tableDefinition;
}
