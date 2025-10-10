package com.cdl.escrow.dto.keycloakdto;

import lombok.Data;

@Data
public class GroupInfo {

    private String groupId;
    private String groupName;

    public GroupInfo(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
