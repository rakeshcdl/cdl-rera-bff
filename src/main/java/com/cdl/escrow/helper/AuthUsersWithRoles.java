package com.cdl.escrow.helper;

import lombok.Data;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Data
public class AuthUsersWithRoles {

    UserRepresentation users;

    List<RoleRepresentation> roles;

}
