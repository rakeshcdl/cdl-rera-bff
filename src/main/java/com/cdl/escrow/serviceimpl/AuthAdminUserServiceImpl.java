
package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.helper.AuthUsersWithRoles;
import com.cdl.escrow.service.AuthAdminUserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthAdminUserServiceImpl implements AuthAdminUserService {

    private final Keycloak keycloak;

    private static final String REALM_NAME = "cdl_rera";

    @Override
    @Transactional(readOnly = true)
    public Page<UserRepresentation> findAllUsers(Pageable pageable, String groupId) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        GroupsResource groups = realm.groups();
        try {
            List<UserRepresentation> usersOfGroup = groups.group(groupId).members();
            if (!usersOfGroup.isEmpty()) {
                final int start = (int) pageable.getOffset();
                final int end = Math.min((start + pageable.getPageSize()), usersOfGroup.size());
                return new PageImpl<>(usersOfGroup.subList(start, end), pageable,
                        usersOfGroup.size());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRepresentation> findAllStaffUsers(String groupId) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        GroupsResource groups = realm.groups();
        try {
            return groups.group(groupId).members();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRepresentation> findAllStaffUsersEntity(String groupId, String entity) throws Exception {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserRepresentation> findAll(Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UsersResource userResource = realm.users();
        try {
            List<UserRepresentation> users = userResource.list();
            if (!users.isEmpty()) {
                final int start = (int) pageable.getOffset();
                final int end = Math.min((start + pageable.getPageSize()), users.size());
                return new PageImpl<>(users.subList(start, end), pageable, users.size());
            } else {
                throw new Exception("No Users Found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRepresentation> findAllUsers() throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        UsersResource userResource = realm.users();
        try {
            List<UserRepresentation> users = userResource.list();
            if (!users.isEmpty()) {

                return users;
            } else {
                throw new Exception("No Users Found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public int save(UserRepresentation user) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        UsersResource userResource = realm.users();
        try {
            user.setEnabled(true);
            user.setEmailVerified(true);
            CredentialRepresentation passwordCredentials = new CredentialRepresentation();
            passwordCredentials.setType(CredentialRepresentation.PASSWORD);
            passwordCredentials.setValue("Cdl@123");
            passwordCredentials.setTemporary(false);
            List<CredentialRepresentation> credentialsList = new ArrayList<>();
            credentialsList.add(passwordCredentials);
            user.setCredentials(credentialsList);
            try (Response response = userResource.create(user)) {
                return response.getStatus();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateUser(UserRepresentation user) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        UsersResource userResource = realm.users();
        try {
            if (Boolean.FALSE.equals(user.isEnabled())) {
                user.setEnabled(false);
            }
            userResource.get(user.getId()).update(user);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserRepresentation findOne(String id) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        UsersResource userResource = realm.users();
        UserResource user = userResource.get(id);
        if (null != user) {
            return user.toRepresentation();
        } else {
            throw new Exception("No User Found With id : " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserRepresentation findOneEntity(String id, String entity) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public void delete(String id) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        UsersResource userResource = realm.users();
        if (null != userResource.get(id)) {
            userResource.get(id).remove();
        } else {
            throw new Exception("No Group Found With Id :" + id);
        }
    }

    @Override
    @Transactional
    public void softDeleteUser(UserRepresentation user) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        UsersResource userResource = realm.users();
        try {
            user.setEnabled(false);
            userResource.get(user.getId()).update(user);
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Page<GroupRepresentation> findGroupMappingOfUser(String userId, Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UsersResource userResource = realm.users();
        UserResource user = userResource.get(userId);
        if (null != user) {
            List<GroupRepresentation> groupMapping = user.groups();
            if (!groupMapping.isEmpty()) {
                final int start = (int) pageable.getOffset();
                final int end = Math.min((start + pageable.getPageSize()), groupMapping.size());

                return new PageImpl<>(groupMapping.subList(start, end), pageable,
                        groupMapping.size());
            } else {
                throw new Exception("No Groups Found For User");
            }
        } else {
            throw new Exception("No User Found With Id :" + userId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleRepresentation> findOneRole(String id) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        RoleMappingResource roles = realm.users().get(id).roles();
        return roles.realmLevel().listAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthUsersWithRoles> findAllRoles() throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        List<AuthUsersWithRoles> usersWithRoles = new ArrayList<>();
        UsersResource userResource = realm.users();
        try {
            List<UserRepresentation> users = userResource.list();
            for (UserRepresentation userRepresentation : users) {
                AuthUsersWithRoles keycloakUsersWithRoles = new AuthUsersWithRoles();
                RoleMappingResource roles = realm.users().get(userRepresentation.getId()).roles();
                keycloakUsersWithRoles.setRoles(roles.realmLevel().listAll());
                keycloakUsersWithRoles.setUsers(userRepresentation);
                usersWithRoles.add(keycloakUsersWithRoles);
            }

        } catch (Exception e) {
            throw e;
        }

        return usersWithRoles;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminEventRepresentation> findAllAdminEvents(String realmName, Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        List<AdminEventRepresentation> adminEvents = realm.getAdminEvents();
        if (!adminEvents.isEmpty()) {
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), adminEvents.size());
            return new PageImpl<>(adminEvents.subList(start, end), pageable,
                    adminEvents.size());
        } else {
            throw new Exception("No Admin Events Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventRepresentation> findAllUserEvents(String realmName, Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        List<EventRepresentation> userEvents = realm.getEvents();

        if (!userEvents.isEmpty()) {
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), userEvents.size());
            return new PageImpl<>(userEvents.subList(start, end), pageable,
                    userEvents.size());
        } else {
            throw new Exception("No User Events Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserSessionRepresentation findUserSession(String id) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UserResource userResource = realm.users().get(id);
        List<UserSessionRepresentation> sessions = userResource.getUserSessions();
        if (!sessions.isEmpty()) {
            sessions.sort(Comparator.comparing(UserSessionRepresentation::getLastAccess).reversed());

            return sessions.get(0); // The session with the latest timestamp is the last login
        } else {
            throw new Exception("No User Found With id : " + id);
        }

    }

    /** Assign multiple realm roles to a user */
    @Override
    @Transactional
    public void assignRolesToUser(String userId, List<String> roleNames) {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UserResource userResource = realm.users().get(userId);

        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> realm.roles().get(roleName).toRepresentation())
                .toList();

        userResource.roles().realmLevel().add(roles);
    }

    /** Assign multiple groups to a user */
    @Override
    @Transactional
    public void assignGroupsToUser(String userId, List<String> groupIds) {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UserResource userResource = realm.users().get(userId);

        for (String groupId : groupIds) {
            GroupRepresentation group = realm.groups().group(groupId).toRepresentation();
            userResource.joinGroup(group.getId());
        }
    }

    /** Remove multiple roles */
    @Override
    @Transactional
    public void removeRolesFromUser(String userId, List<String> roleNames) {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UserResource userResource = realm.users().get(userId);

        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> realm.roles().get(roleName).toRepresentation())
                .toList();

        userResource.roles().realmLevel().remove(roles);
    }

    /** Remove multiple groups */
    @Override
    @Transactional
    public void removeGroupsFromUser(String userId, List<String> groupIds) {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UserResource userResource = realm.users().get(userId);

        for (String groupId : groupIds) {
            userResource.leaveGroup(groupId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllUsersWithRolesAndGroups() {
        RealmResource realmResource = keycloak.realm(REALM_NAME);

        List<UserRepresentation> users = realmResource.users().list();
        List<Map<String, Object>> userDetailsList = new ArrayList<>();

        for (UserRepresentation user : users) {
            UserResource userResource = realmResource.users().get(user.getId());

            // Get groups (id + name)
            List<Map<String, String>> groups = userResource.groups()
                    .stream()
                    .map(group -> {
                        Map<String, String> g = new HashMap<>();
                        g.put("id", group.getId());
                        g.put("name", group.getName());
                        return g;
                    })
                    .collect(Collectors.toList());

            // Get roles (id + name)
            List<Map<String, String>> roles = userResource.roles()
                    .realmLevel()
                    .listEffective()
                    .stream()
                    .map(role -> {
                        Map<String, String> r = new HashMap<>();
                        r.put("id", role.getId());
                        r.put("name", role.getName());
                        return r;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userId", user.getId());
            userDetails.put("username", user.getUsername());
            userDetails.put("firstname", user.getFirstName());
            userDetails.put("lastname", user.getLastName());
            userDetails.put("email", user.getEmail());
            userDetails.put("groups", groups);
            userDetails.put("roles", roles);

            userDetailsList.add(userDetails);
        }

        return userDetailsList;

    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUserRolesAndGroups(String userId) {
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        UserResource userResource = realmResource.users().get(userId);
        UserRepresentation user = userResource.toRepresentation();

        // Groups
        List<Map<String, String>> groups = userResource.groups()
                .stream()
                .map(group -> {
                    Map<String, String> g = new HashMap<>();
                    g.put("id", group.getId());
                    g.put("name", group.getName());
                    return g;
                })
                .collect(Collectors.toList());

        // Realm Roles
        List<Map<String, String>> realmRoles = userResource.roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(role -> {
                    Map<String, String> r = new HashMap<>();
                    r.put("id", role.getId());
                    r.put("name", role.getName());
                    return r;
                })
                .collect(Collectors.toList());

        // Client Roles
        Map<String, List<Map<String, String>>> clientRoles = new HashMap<>();
        realmResource.clients().findAll().forEach(client -> {
            List<RoleRepresentation> clientRoleList = userResource.roles()
                    .clientLevel(client.getId())
                    .listEffective();
            if (!clientRoleList.isEmpty()) {
                List<Map<String, String>> roleList = clientRoleList.stream()
                        .map(role -> {
                            Map<String, String> r = new HashMap<>();
                            r.put("id", role.getId());
                            r.put("name", role.getName());
                            return r;
                        })
                        .collect(Collectors.toList());
                clientRoles.put(client.getClientId(), roleList);
            }
        });

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("userId", user.getId());
        userDetails.put("username", user.getUsername());
        userDetails.put("firstname", user.getFirstName());
        userDetails.put("lastname", user.getLastName());
        userDetails.put("email", user.getEmail());
        userDetails.put("groups", groups);
        userDetails.put("realmRoles", realmRoles);
        userDetails.put("clientRoles", clientRoles);

        return userDetails;
    }
}
