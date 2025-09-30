

package com.cdl.escrow.service;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthAdminRoleService {

    int save(RoleRepresentation role);

    int save(List<RoleRepresentation> roles);

    void update(String roleName,String updateRoleName) throws Exception;

    Page<RoleRepresentation> findAll(Pageable pageable) throws Exception;

    RoleRepresentation findOne(String roleName) throws Exception;

    void delete(String id) throws Exception;
}
