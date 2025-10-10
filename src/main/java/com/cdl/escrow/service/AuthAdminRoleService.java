

package com.cdl.escrow.service;

import com.cdl.escrow.dto.keycloakdto.RoleFindRequest;
import com.cdl.escrow.dto.keycloakdto.RoleGroupsResponse;
import com.cdl.escrow.dto.keycloakdto.RoleMappingRequest;
import com.cdl.escrow.dto.keycloakdto.RoleMappingResponse;
import jakarta.validation.Valid;
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

    RoleMappingResponse mapRoleToGroups(RoleMappingRequest request);

    RoleMappingResponse unmapRoleFromGroups(@Valid RoleMappingRequest request);

    RoleGroupsResponse findGroupsForRoles(RoleFindRequest request);
}
