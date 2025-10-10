package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

import java.util.List;

@Data
public class RoleMappingResponse {
    private String roleName;
    private RoleMappingRequest.RoleType roleType;
    private List<GroupMappingResult> results;
}
