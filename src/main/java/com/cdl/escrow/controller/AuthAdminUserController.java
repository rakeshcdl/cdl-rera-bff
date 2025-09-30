package com.cdl.escrow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.AuthUsersWithRoles;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.helper.UserRoleGroupRequest;
import com.cdl.escrow.service.AuthAdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth-admin-user")
@RequiredArgsConstructor
@Slf4j
public class AuthAdminUserController {

    private static final String ENTITY_NAME = "AUTH_ADMIN_USER";

    private final AuthAdminUserService authAdminUserService;

    @GetMapping("/auth/groups/{id}/users")
    public ResponseEntity<List<UserRepresentation>> getUsersFromGroup(@PathVariable String id, Pageable pageable) throws Exception {
        Page<UserRepresentation> users;
        try {
            users = authAdminUserService.findAllUsers(pageable, id);
            if (null != users) {
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), users);
                return ResponseEntity.ok().headers(headers).body((null != users) ? users.getContent() : null);
            } else {
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/users")
    public ResponseEntity<List<UserRepresentation>> getUsersOfRealm(Pageable pageable) throws Exception {
        Page<UserRepresentation> users;
        try {
            users = authAdminUserService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), users);
            return ResponseEntity.ok().headers(headers).body(users.getContent());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/auth/users")
    public ResponseEntity<Integer> createUser(@RequestBody UserRepresentation user) throws Exception {
        log.debug("REST request to save User : {}", user);
        if (user.getId() != null) {
            throw new Exception("A new user cannot already have an ID");
        }
        int status = authAdminUserService.save(user);
        if (status != 201) {
            log.debug("Unable to create. A user with name {} already exist", user.getUsername());
            throw new BadRequestAlertException("A new user cannot create already have same username",ENTITY_NAME, "nameexists");
        }

        return ResponseEntity.ok().body(status);
    }

    @PutMapping("/auth/users/{id}")
    public ResponseEntity<Integer> updateUser(
            @PathVariable(value = "id", required = false) final String id,
            @RequestBody UserRepresentation user
    ) throws Exception {
        log.debug("REST request to update User : {}, {}", id, user);
        if (user.getId() == null) {
            throw new Exception("Invalid id");
        }

        try {
            authAdminUserService.updateUser(user);
            return ResponseEntity.ok().body(201);
        } catch (Exception e) {
            return ResponseEntity.ok().body(491);
        }
    }

    @GetMapping("/auth/users/{id}")
    public UserRepresentation getUser(@PathVariable String id) throws Exception {
        log.debug("REST request to get Group : {}", id);
        UserRepresentation user;
        try {
            user = authAdminUserService.findOne(id);
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/auth/users/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable String id) throws Exception {
        log.debug("REST request to delete User : {}", id);
        try {
            authAdminUserService.delete(id);
            return ResponseEntity.ok().body(201);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/users/{id}/group-mapping")
    public ResponseEntity<List<GroupRepresentation>> getGroupMapping(@PathVariable String id, Pageable pageable) throws Exception {
        Page<GroupRepresentation> groups;
        try {
            groups = authAdminUserService.findGroupMappingOfUser(id, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), groups);
            return ResponseEntity.ok().headers(headers).body(groups.getContent());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/users/roles/{id}")
    public List<RoleRepresentation>   getRoleByUser(@PathVariable String id) throws Exception {

        List<RoleRepresentation>  roles;
        try {
            roles = authAdminUserService.findOneRole(id);
            return roles;
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/users/roles")
    public List<AuthUsersWithRoles>   getAllRoleByUser() throws Exception {
        log.debug("REST request to get Group");
        List<AuthUsersWithRoles>  roles;
        try {
            roles = authAdminUserService.findAllRoles();
            return roles;
        } catch (Exception e) {
            throw e;
        }
    }



    @GetMapping("/auth/users/{realm}/admin-events")
    public ResponseEntity<List<AdminEventRepresentation>> getAdminEventsOfRealm(@PathVariable String realm, @ParameterObject @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) throws Exception {
        Page<AdminEventRepresentation> adminEvents;
        try {
            adminEvents = authAdminUserService.findAllAdminEvents(realm ,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), adminEvents);
            return ResponseEntity.ok().headers(headers).body(adminEvents.getContent());
        } catch (Exception e) {
            throw e;
        }
    }


    @GetMapping("/auth/users/{realm}/user-events")
    public ResponseEntity<List<EventRepresentation>> getUserEventsOfRealm(@PathVariable String realm, @ParameterObject @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) throws Exception {
        Page<EventRepresentation> userEvents;
        try {
            userEvents = authAdminUserService.findAllUserEvents(realm ,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), userEvents);
            return ResponseEntity.ok().headers(headers).body(userEvents.getContent());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/users/{id}/sessions")
    public UserSessionRepresentation getUserSession(@PathVariable String id) throws Exception {
        log.debug("REST request to get Group : {}", id);
        UserSessionRepresentation user;
        try {
            user = authAdminUserService.findUserSession(id);
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/auth/users/soft/delete/{id}")
    public ResponseEntity<Integer> softDeleteUser(
            @PathVariable(value = "id", required = false) final String id,
            @RequestBody UserRepresentation user
    ) throws Exception {
        log.debug("REST request to delete User : {}, {}", id, user);
        if (user.getId() == null) {
            throw new Exception("Invalid id");
        }

        try {
            authAdminUserService.softDeleteUser(user);
            return ResponseEntity.ok().body(201);
        } catch (Exception e) {
            return ResponseEntity.ok().body(491);
        }
    }

    // API to map roles and permission based in user id

    @PostMapping("/auth/users/assign/roles-permissions")
    public ResponseEntity<String> createUserRolePermissionMapping(@RequestBody UserRoleGroupRequest userRoleGroupRequest) throws Exception {
        log.debug("REST request to save User roles and permission: {}", userRoleGroupRequest);

        if (userRoleGroupRequest.getRoles() != null && !userRoleGroupRequest.getRoles().isEmpty()) {
            authAdminUserService.assignRolesToUser(userRoleGroupRequest.getUserId(), userRoleGroupRequest.getRoles());
        }
        if (userRoleGroupRequest.getGroups() != null && !userRoleGroupRequest.getGroups().isEmpty()) {
            authAdminUserService.assignGroupsToUser(userRoleGroupRequest.getUserId(), userRoleGroupRequest.getGroups());
        }

        return ResponseEntity.ok().body(userRoleGroupRequest.getUserId());
    }

    @PostMapping("/auth/users/un-assign/roles-permissions")
    public ResponseEntity<String> removeUserRolePermissionMapping(@RequestBody UserRoleGroupRequest userRoleGroupRequest) throws Exception {
        log.debug("REST request to remove User roles and permission: {}", userRoleGroupRequest);

        if (userRoleGroupRequest.getRoles() != null && !userRoleGroupRequest.getRoles().isEmpty()) {
            authAdminUserService.removeRolesFromUser(userRoleGroupRequest.getUserId(), userRoleGroupRequest.getRoles());
        }
        if (userRoleGroupRequest.getGroups() != null && !userRoleGroupRequest.getGroups().isEmpty()) {
            authAdminUserService.removeGroupsFromUser(userRoleGroupRequest.getUserId(), userRoleGroupRequest.getGroups());
        }

        return ResponseEntity.ok().body(userRoleGroupRequest.getUserId());
    }

    @GetMapping("/auth/users/roles-groups")
    public List<Map<String, Object>> getAllUsersWithRolesAndGroups() throws Exception {
        log.debug("REST request to get all roles and Group");
        return authAdminUserService.getAllUsersWithRolesAndGroups();
    }

    @GetMapping("/auth/users/{userId}/roles-groups")
    public Map<String, Object> getUsersWithRolesAndGroups(@PathVariable String userId) throws Exception {
        log.debug("REST request to get all roles and Group {}", userId);
        return authAdminUserService.getUserRolesAndGroups(userId);
    }

}
