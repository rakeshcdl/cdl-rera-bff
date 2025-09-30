package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RealEstateDocumentCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter rea;

    private StringFilter documentName;

    private StringFilter content;

    private StringFilter location;

    private StringFilter module;

    private LongFilter recordId;

    private StringFilter storageType;

    private ZonedDateTimeFilter uploadDate;

    private StringFilter documentSize;

    private ZonedDateTimeFilter validityDate;

    private StringFilter eventDetail;

    private LongFilter documentTypeId;
}
