package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

@Data
public class RoleQuery {

    public enum RoleType { REALM, CLIENT }

    private RoleType roleType;
    private String roleName;
    private String clientId; // required only when roleType == CLIENT
}
