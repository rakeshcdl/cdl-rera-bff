/**
 * ZonedDateTimeFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ZonedDateTimeFilter extends Filter<ZonedDateTime> {
    private ZonedDateTime greaterThan;
    private ZonedDateTime lessThan;
    private ZonedDateTime greaterThanOrEqual;
    private ZonedDateTime lessThanOrEqual;

    private List<ZonedDateTime> in;
    private List<ZonedDateTime> notIn;
}
