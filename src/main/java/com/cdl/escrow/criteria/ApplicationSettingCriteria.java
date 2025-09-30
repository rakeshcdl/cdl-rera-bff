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
public class ApplicationSettingCriteria implements Serializable {
    private LongFilter id ;

    private StringFilter settingKey;

    private StringFilter settingValue;

    private StringFilter languageTranslationId;

    private StringFilter remarks;
}
