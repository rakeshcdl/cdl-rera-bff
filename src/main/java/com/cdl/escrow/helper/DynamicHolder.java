/**
 * DynamicHolder.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */


package com.cdl.escrow.helper;

import lombok.Data;

import java.util.List;

@Data
public class DynamicHolder {

    String groupId;

    List<String> userIds;

    List<String> roleNames;
}
