package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleGroupsResult {

    private RoleQuery role;
    private List<GroupInfo> groups = new ArrayList<>();
}
