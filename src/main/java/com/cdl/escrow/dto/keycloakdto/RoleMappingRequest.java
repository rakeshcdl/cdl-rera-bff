package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

import java.util.List;

@Data
public class RoleMappingRequest {

    public enum RoleType { REALM, CLIENT }

    private RoleType roleType;
    private String roleName;
    private String clientId;        // required when roleType == CLIENT (the clientId, not the UUID)
    private List<String> groupIds;  // list of group UUIDs
}
