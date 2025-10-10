package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleGroupsResponse {
    private List<RoleGroupsResult> results = new ArrayList<>();
}
