/**
 * StringFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StringFilter extends Filter<String> {
    private String equals;
    private List<String> in;
    private List<String> notIn;
    private String contains;
    private String doesNotContain;

    // new ones:
    private String startsWith;
    private String endsWith;
    private String like;
}
