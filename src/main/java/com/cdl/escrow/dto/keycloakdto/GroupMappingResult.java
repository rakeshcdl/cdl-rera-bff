package com.cdl.escrow.dto.keycloakdto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupMappingResult {
    private String groupId;
    private boolean success;
    private String message;

    public GroupMappingResult(String groupId, boolean success, String message) {
        this.groupId = groupId;
        this.success = success;
        this.message = message;
    }

}
