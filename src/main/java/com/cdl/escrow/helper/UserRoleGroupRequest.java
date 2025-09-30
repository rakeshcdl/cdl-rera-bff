package com.cdl.escrow.helper;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRoleGroupRequest implements Serializable {

    private String userId;
    private List<String> roles;
    private List<String> groups;
}
