package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

import java.util.List;

@Data
public class RoleFindRequest {
    private List<RoleQuery> roles;
}
