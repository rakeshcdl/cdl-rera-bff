/**
 * AuthAdminRoleServiceImpl.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */


package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.keycloakdto.*;
import com.cdl.escrow.service.AuthAdminRoleService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthAdminRoleServiceImpl implements AuthAdminRoleService {

    private final Keycloak keycloak;

    private static final String REALM_NAME = "cdl_rera";

    @Override
    @Transactional
    public int save(RoleRepresentation role) {
        RealmResource realm = keycloak.realm(REALM_NAME);

        RolesResource realmRoles = realm.roles();
        try {
            realmRoles.create(role);
            return 201;
        } catch (Exception e) {
            return 491;
        }
    }

    @Override
    @Transactional
    public int save(List<RoleRepresentation> roles) {
        RealmResource realm = keycloak.realm(REALM_NAME);

        RolesResource realmRoles = realm.roles();
        try {
            roles.forEach(realmRoles::create);
            return 201;
        } catch (Exception e) {
            return 491;
        }
    }

    @Override
    @Transactional
    public void update(String roleName,String updateRoleName) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        RolesResource roleResource = realm.roles();

        RoleResource roleData = roleResource.get(roleName);

        RoleRepresentation roleRepresentation = roleData.toRepresentation();
        roleRepresentation.setName(updateRoleName);
        try {
            roleResource.get(roleName).update(roleData.toRepresentation());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleRepresentation> findAll(Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        List<RoleRepresentation> roles = realm.roles().list();
        if (!roles.isEmpty()) {
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), roles.size());
            return new PageImpl<>(roles.subList(start, end), pageable, roles.size());
        } else {
            throw new Exception("No Roles Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RoleRepresentation findOne(String roleName) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        RolesResource roleResource = realm.roles();
        RoleResource role = roleResource.get(roleName);
        if (null != role) {
            return role.toRepresentation();
        } else {
            throw new Exception("No Role Found");
        }
    }

    @Override
    @Transactional
    public void delete(String name) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        RolesResource roleResource = realm.roles();
        if (null != roleResource.get(name).toRepresentation()) {
            roleResource.deleteRole(name);
        } else {
            throw new Exception("No Group Found with Id :" + name);
        }
    }

    @Override
    public RoleMappingResponse mapRoleToGroups(RoleMappingRequest req) {
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        RoleMappingResponse resp = new RoleMappingResponse();
        resp.setRoleName(req.getRoleName());
        resp.setRoleType(req.getRoleType());
        List<GroupMappingResult> results = new ArrayList<>();

// Resolve role representation(s) we will add
        try {
            if (req.getRoleType() == RoleMappingRequest.RoleType.REALM) {
                RoleRepresentation roleRep = realmResource.roles().get(req.getRoleName()).toRepresentation();
                if (roleRep == null) {
                    throw new IllegalArgumentException("Realm role not found: " + req.getRoleName());
                }
                // For each group, add the realm role
                for (String groupId : nonNullList(req.getGroupIds())) {
                    try {
                        GroupResource groupResource = realmResource.groups().group(groupId);
                        if (groupResource == null) {
                            results.add(new GroupMappingResult(groupId, false, "Group not found"));
                            continue;
                        }
                        groupResource.roles().realmLevel().add(Collections.singletonList(roleRep));
                        results.add(new GroupMappingResult(groupId, true, "Mapped to realm role"));
                    } catch (Exception ex) {
                        log.error("Failed to map realm role {} to group {}", req.getRoleName(), groupId, ex);
                        results.add(new GroupMappingResult(groupId, false, "Error: " + ex.getMessage()));
                    }
                }
            } else { // CLIENT role
                if (req.getClientId() == null || req.getClientId().isBlank()) {
                    throw new IllegalArgumentException("clientId is required for client roles");
                }

                // Find client by clientId and get its internal id (UUID)
                List<ClientRepresentation> found = realmResource.clients().findByClientId(req.getClientId());
                if (found == null || found.isEmpty()) {
                    throw new IllegalArgumentException("Client not found: " + req.getClientId());
                }
                String clientUuid = found.getFirst().getId();
                // Get client role representation
                //RoleRepresentation clientRoleRep = realmResource.clients().get(clientUuid).roles().get(req.getRoleName()).toRepresentation();
                RolesResource roleResource = realmResource.roles();

                RoleResource roleData = roleResource.get(req.getRoleName());

                RoleRepresentation clientRoleRep = roleData.toRepresentation();

                if (clientRoleRep == null) {
                    throw new IllegalArgumentException("Client role not found: " + req.getRoleName() + " for client " + req.getClientId());
                }
                for (String groupId : nonNullList(req.getGroupIds())) {
                    try {
                        GroupResource groupResource = realmResource.groups().group(groupId);
                        if (groupResource == null) {
                            results.add(new GroupMappingResult(groupId, false, "Group not found"));
                            continue;
                        }
                        groupResource.roles().clientLevel(clientUuid).add(Collections.singletonList(clientRoleRep));
                        results.add(new GroupMappingResult(groupId, true, "Mapped to client role"));
                    } catch (Exception ex) {
                        log.error("Failed to map client role {} (client {}) to group {}", req.getRoleName(), req.getClientId(), groupId, ex);
                        results.add(new GroupMappingResult(groupId, false, "Error: " + ex.getMessage()));
                    }
                }
            }

            resp.setResults(results);
            return resp;
        } catch (IllegalArgumentException iae) {
            // throw so controller can return 4xx
            throw iae;
        } catch (Exception e) {
            log.error("Unexpected error mapping role to groups", e);
            throw new RuntimeException("Mapping failed: " + e.getMessage(), e);
        }

    }

    /**
     * Remove/unmap the specified role (realm or client) from multiple groups.
     * Idempotent: attempting to remove a mapping that doesn't exist is treated as success.
     */
    public RoleMappingResponse unmapRoleFromGroups(RoleMappingRequest req) {
        RoleMappingResponse resp = new RoleMappingResponse();
        resp.setRoleName(req.getRoleName());
        resp.setRoleType(req.getRoleType());
        List<GroupMappingResult> results = new ArrayList<>();

        RealmResource realmResource = keycloak.realm(REALM_NAME);

        try {
            if (req.getRoleType() == RoleMappingRequest.RoleType.REALM) {
               // RoleRepresentation roleRep = realmResource.roles().get(req.getRoleName()).toRepresentation();
                RolesResource roleResource = realmResource.roles();

                RoleResource roleData = roleResource.get(req.getRoleName());

                RoleRepresentation roleRep = roleData.toRepresentation();
                if (roleRep == null) {
                    throw new IllegalArgumentException("Realm role not found: " + req.getRoleName());
                }

                for (String groupId : nonNullList(req.getGroupIds())) {
                    try {
                        GroupResource groupResource = realmResource.groups().group(groupId);
                        if (groupResource == null) {
                            results.add(new GroupMappingResult(groupId, false, "Group not found"));
                            continue;
                        }

                        // Remove realm-level mapping
                        groupResource.roles().realmLevel().remove(Collections.singletonList(roleRep));

                        // If Keycloak didn't throw, we treat as success (idempotent)
                        results.add(new GroupMappingResult(groupId, true, "Unmapped from realm role"));
                    } catch (NotFoundException nfe) {
                        // group or role not found
                        results.add(new GroupMappingResult(groupId, false, "Group or role not found: " + nfe.getMessage()));
                    } catch (Exception ex) {
                        log.error("Failed to unmap realm role {} from group {}", req.getRoleName(), groupId, ex);
                        results.add(new GroupMappingResult(groupId, false, "Error: " + ex.getMessage()));
                    }
                }

            } else { // CLIENT role
                if (req.getClientId() == null || req.getClientId().isBlank()) {
                    throw new IllegalArgumentException("clientId is required for client roles");
                }

                List<ClientRepresentation> found = realmResource.clients().findByClientId(req.getClientId());
                if (found == null || found.isEmpty()) {
                    throw new IllegalArgumentException("Client not found: " + req.getClientId());
                }
                String clientUuid = found.get(0).getId();

                RoleRepresentation clientRoleRep = realmResource.clients().get(clientUuid).roles().get(req.getRoleName()).toRepresentation();
                if (clientRoleRep == null) {
                    throw new IllegalArgumentException("Client role not found: " + req.getRoleName() + " for client " + req.getClientId());
                }

                for (String groupId : nonNullList(req.getGroupIds())) {
                    try {
                        GroupResource groupResource = realmResource.groups().group(groupId);
                        if (groupResource == null) {
                            results.add(new GroupMappingResult(groupId, false, "Group not found"));
                            continue;
                        }

                        groupResource.roles().clientLevel(clientUuid).remove(Collections.singletonList(clientRoleRep));
                        results.add(new GroupMappingResult(groupId, true, "Unmapped from client role"));
                    } catch (NotFoundException nfe) {
                        results.add(new GroupMappingResult(groupId, false, "Group or role not found: " + nfe.getMessage()));
                    } catch (Exception ex) {
                        log.error("Failed to unmap client role {} (client {}) from group {}", req.getRoleName(), req.getClientId(), groupId, ex);
                        results.add(new GroupMappingResult(groupId, false, "Error: " + ex.getMessage()));
                    }
                }
            }

            resp.setResults(results);
            return resp;

        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception e) {
            log.error("Unexpected error unmapping role from groups", e);
            throw new RuntimeException("Unmapping failed: " + e.getMessage(), e);
        }
    }

    @Override
    public RoleGroupsResponse findGroupsForRoles(RoleFindRequest request) {
        if (request == null || request.getRoles() == null || request.getRoles().isEmpty()) {
            throw new IllegalArgumentException("roles list is required");
        }

        RealmResource realmResource = keycloak.realm(REALM_NAME);

        // Fetch all groups once (careful on very large realms — consider pagination if needed)
        List<GroupRepresentation> allGroups = realmResource.groups().groups();
        if (allGroups == null) {
            allGroups = Collections.emptyList();
        }

        RoleGroupsResponse response = new RoleGroupsResponse();

        for (RoleQuery rq : request.getRoles()) {
            if (rq == null || rq.getRoleName() == null || rq.getRoleName().isBlank()) {
                throw new IllegalArgumentException("Each role must contain roleType and roleName");
            }

            RoleGroupsResult rgResult = new RoleGroupsResult();
            rgResult.setRole(rq);

            // Resolve role representation (realm or client)
            RoleRepresentation roleRep;
            String clientUuid = null;
            if (rq.getRoleType() == RoleQuery.RoleType.REALM) {
                try {
                    RolesResource roleResource = realmResource.roles();

                    RoleResource roleData = roleResource.get(rq.getRoleName());
                   // roleRep = realmResource.roles().get(rq.getRoleName()).toRepresentation();
                    roleRep = roleData.toRepresentation();
                    if (roleRep == null) {
                        throw new IllegalArgumentException("Realm role not found: " + rq.getRoleName());
                    }
                } catch (NotFoundException e) {
                    throw new IllegalArgumentException("Realm role not found: " + rq.getRoleName());
                }
            } else { // CLIENT
                if (rq.getClientId() == null || rq.getClientId().isBlank()) {
                    throw new IllegalArgumentException("clientId is required for client roles");
                }
                List<ClientRepresentation> clients = realmResource.clients().findByClientId(rq.getClientId());
                if (clients == null || clients.isEmpty()) {
                    throw new IllegalArgumentException("Client not found: " + rq.getClientId());
                }
                clientUuid = clients.get(0).getId();
                try {
                    roleRep = realmResource.clients().get(clientUuid).roles().get(rq.getRoleName()).toRepresentation();
                    if (roleRep == null) {
                        throw new IllegalArgumentException("Client role not found: " + rq.getRoleName());
                    }
                } catch (NotFoundException e) {
                    throw new IllegalArgumentException("Client role not found: " + rq.getRoleName());
                }
            }

            // For each group, check mapping presence and collect those that have the role
            List<GroupInfo> mappedGroups = new ArrayList<>();
            for (GroupRepresentation g : allGroups) {
                try {
                    GroupResource groupResource = realmResource.groups().group(g.getId());
                    boolean mapped;
                    if (rq.getRoleType() == RoleQuery.RoleType.REALM) {
                        List<RoleRepresentation> current = groupResource.roles().realmLevel().listAll();
                        mapped = current.stream().anyMatch(r -> r.getName().equals(roleRep.getName()));
                    } else {
                        List<RoleRepresentation> currentClient = groupResource.roles().clientLevel(clientUuid).listAll();
                        mapped = currentClient.stream().anyMatch(r -> r.getName().equals(roleRep.getName()));
                    }

                    if (mapped) {
                        mappedGroups.add(new GroupInfo(g.getId(), g.getName()));
                    }
                } catch (Exception ex) {
                    // log and continue — do not fail the entire result for one group
                    log.warn("Failed to check mapping for group {}: {}", g.getId(), ex.getMessage());
                }
            }

            rgResult.setGroups(mappedGroups);
            response.getResults().add(rgResult);
        }

        return response;
    }


    private List<String> nonNullList(List<String> in) {
        return (in == null) ? Collections.emptyList() : in;
    }

}
