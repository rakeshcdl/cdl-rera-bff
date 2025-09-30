package com.cdl.escrow.controller;

import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.service.AuthAdminRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth-admin-user")
@RequiredArgsConstructor
@Slf4j
public class AuthAdminRoleController {

    private static final String ENTITY_NAME = "AUTH_ADMIN_ROLE";

    private final AuthAdminRoleService authAdminRoleService;

    @PostMapping("/auth/roles")
    public ResponseEntity<Integer> createRole(@RequestBody RoleRepresentation role) throws Exception {
        log.debug("REST request to save Role : {}", role);
        if (role.getId() != null) {
            throw new Exception("A new role cannot already have an ID");
        }
        int status = authAdminRoleService.save(role);
        if (status == 491) {
            throw new Exception("Role cannot be created");
        }
        return ResponseEntity.ok().body(status);
    }

    @PutMapping("/auth/roles/{role_name}/{update_role_name}")
    public ResponseEntity<Integer> updateRole(
            @PathVariable(value = "role_name", required = false) final String roleName
            , @PathVariable(value = "update_role_name", required = false) final String updatedRoleName
    ) throws Exception {
        log.debug("REST request to update Role : {}", roleName);

        try {
            authAdminRoleService.update(roleName,updatedRoleName);
            return ResponseEntity.ok().body(201);
        } catch (Exception e) {
            throw new Exception("Role couldn't be updated");
        }
    }

    @GetMapping("/auth/roles")
    public ResponseEntity<List<RoleRepresentation>> getAllRoles(Pageable pageable) throws Exception {
        Page<RoleRepresentation> rolesFromContainer;
        try {
            rolesFromContainer = authAdminRoleService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                    ServletUriComponentsBuilder.fromCurrentRequest(),
                    rolesFromContainer
            );
            return ResponseEntity.ok().headers(headers).body(rolesFromContainer.getContent());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/roles/{name}")
    public ResponseEntity<RoleRepresentation> getRole(@PathVariable String name) throws Exception {

        RoleRepresentation role;
        try {
            role = authAdminRoleService.findOne(name);
            return ResponseEntity.ok().body(role);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/auth/roles/{name}")
    public ResponseEntity<Integer> deleteRole(@PathVariable String name) throws Exception {
        log.debug("REST request to delete Role : {}", name);
        authAdminRoleService.delete(name);
        return ResponseEntity.ok().body(201);
    }

    @PostMapping("/auth/assign-roles")
    public ResponseEntity<Integer> createMultipleRole(@RequestBody List<RoleRepresentation> roles) throws Exception {

        if (roles.isEmpty()) {
            throw new Exception("Roles Passed Cannot be Empty");
        }
        int status = authAdminRoleService.save(roles);
        if (status == 491) {
            throw new Exception("Role cannot be created");
        }
        return ResponseEntity.ok().body(status);
    }
}
