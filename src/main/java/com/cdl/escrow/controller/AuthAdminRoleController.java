package com.cdl.escrow.controller;

import com.cdl.escrow.dto.keycloakdto.*;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.service.AuthAdminRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

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

    // Roles and groups mappings
    @PostMapping("/roles/map-groups")
    public ResponseEntity<?> mapRoleToGroups(@Valid @RequestBody RoleMappingRequest request) {
        try {
            request.setRoleType(RoleMappingRequest.RoleType.REALM);
            request.setClientId("webapiaccess");
            RoleMappingResponse resp = authAdminRoleService.mapRoleToGroups(request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid_request", "message", iae.getMessage()));
        } catch (Exception ex) {
            log.error("Mapping error", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "internal_error", "message", ex.getMessage()));
        }
    }

    @PostMapping("/roles/unmap-groups")
    public ResponseEntity<?> unmapRoleFromGroups(@Valid @RequestBody RoleMappingRequest request) {
        try {
            request.setRoleType(RoleMappingRequest.RoleType.REALM);
            request.setClientId("webapiaccess");
            RoleMappingResponse resp = authAdminRoleService.unmapRoleFromGroups(request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid_request", "message", iae.getMessage()));
        } catch (Exception ex) {
            log.error("Unmapping error", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "internal_error", "message", ex.getMessage()));
        }
    }

    @PostMapping("/roles/find-groups-for-roles")
    public ResponseEntity<?> findGroupsForRoles(@RequestBody RoleFindRequest request) {
        try {
            List<RoleQuery> rolesList = Optional.ofNullable(request.getRoles()).orElse(Collections.emptyList());

            if (rolesList.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "invalid_request",
                        "message", "roles list is required and must not be empty"
                ));
            }

            // Create a new list and apply defaults (do not modify the incoming list while iterating)
            List<RoleQuery> normalized = new ArrayList<>(rolesList.size());
            for (RoleQuery r : rolesList) {
                if (r == null || r.getRoleName() == null || r.getRoleName().isBlank()) {
                    return ResponseEntity.badRequest().body(Map.of(
                            "error", "invalid_request",
                            "message", "each role entry must contain a roleName"
                    ));
                }
                RoleQuery copy = new RoleQuery();
                copy.setRoleName(r.getRoleName().trim());

                // default roleType to REALM if not provided
                copy.setRoleType(r.getRoleType() == null ? RoleQuery.RoleType.REALM : r.getRoleType());

                // optionally set a default clientId only if this is a CLIENT role and clientId omitted
                if (copy.getRoleType() == RoleQuery.RoleType.CLIENT) {
                    copy.setClientId(
                            (r.getClientId() == null || r.getClientId().isBlank()) ? null : r.getClientId().trim()
                    );
                } else {
                    // For REALM roles, clientId is irrelevant - you may still set a harmless default if you want
                    copy.setClientId(null);
                }

                normalized.add(copy);
            }

            // If you want to force a specific clientId for all roles of type CLIENT uncomment below:
            // normalized.stream()
            //     .filter(q -> q.getRoleType() == RoleQuery.RoleType.CLIENT && q.getClientId() == null)
            //     .forEach(q -> q.setClientId("webapiaccess"));

            // Replace the request roles with normalized list (optional)
            request.setRoles(normalized);

            log.debug("Roles to process: {}", normalized);
            RoleGroupsResponse resp = authAdminRoleService.findGroupsForRoles(request);
            return ResponseEntity.ok(resp);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid_request", "message", iae.getMessage()));
        } catch (Exception ex) {
            log.error("Error finding groups for roles", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "internal_error", "message", ex.getMessage()));
        }
    }

}
