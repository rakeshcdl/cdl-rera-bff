/**
 * LocalDateTimeFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocalDateTimeFilter extends Filter<LocalDateTime> {
    private LocalDateTime greaterThan;
    private LocalDateTime lessThan;
    private LocalDateTime greaterThanOrEqual;
    private LocalDateTime lessThanOrEqual;

    private List<LocalDateTime> in;
    private List<LocalDateTime> notIn;
}
