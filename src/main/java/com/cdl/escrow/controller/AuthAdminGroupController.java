package com.cdl.escrow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.DynamicHolder;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.service.AuthAdminGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth-admin-group")
@RequiredArgsConstructor
@Slf4j
public class AuthAdminGroupController {

    private static final String ENTITY_NAME = "AUTH_ADMIN_GROUP";

    private final AuthAdminGroupService authAdminGroupService;

    @PostMapping("/auth/groups")
    public ResponseEntity<Integer> createGroup(@RequestBody GroupRepresentation group) throws Exception {
        log.debug("REST request to save Group : {}", group);
        if (group.getId() != null) {
            throw new Exception("A new group cannot already have an ID");
        }
        int status = authAdminGroupService.save(group);
        if (status != 201) {
            log.debug("Unable to create. A group with name {} already exist", group.getName());
            throw new BadRequestAlertException("A new group cannot create already have same group name", ENTITY_NAME, "nameexists");
        }
        return ResponseEntity.ok().body(status);
    }

    @PutMapping("/auth/groups/{id}")
    public ResponseEntity<Integer> updateGroup(
            @PathVariable(value = "id", required = false) final String id,
            @RequestBody GroupRepresentation group
    ) throws Exception {
        log.debug("REST request to update Group : {}, {}", id, group);
        if (group.getId() == null) {
            throw new Exception("Invalid id");
        }

        try {
            authAdminGroupService.updateGroup(group);
            return ResponseEntity.ok().body(201);
        } catch (Exception e) {
            throw new Exception("Group couldn't be updated");
        }
    }
    @GetMapping("/auth/groups")
    public ResponseEntity<List<GroupRepresentation>> getAllGroups(Pageable pageable) throws Exception {
        Page<GroupRepresentation> groups;
        try {
            groups = authAdminGroupService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), groups);
            return ResponseEntity.ok().headers(headers).body(groups.getContent());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/groups/{id}")
    public GroupRepresentation getGroup(@PathVariable String id) throws Exception {
        log.debug("REST request to get Group : {}", id);
        GroupRepresentation group;
        try {
            group = authAdminGroupService.findOne(id);
            return group;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/auth/groups/{id}")
    public ResponseEntity<Integer> deleteGroup(@PathVariable String id) throws Exception {
        log.debug("REST request to delete Group : {}", id);
        try {
            authAdminGroupService.delete(id);
            return ResponseEntity.ok().body(201);
        } catch (Exception e) {
            throw e;
        }
    }
    @PostMapping("/auth/user-group-management/{groupId}")
    public void createUserGroupMapping(@PathVariable String groupId, @RequestBody DynamicHolder holder) throws Exception {

        if (null == holder.getGroupId() || holder.getUserIds().isEmpty()) {
            throw new Exception("Group Id & User Ids are mandatory");
        }
        try {
            authAdminGroupService.saveUserGroupMapping(holder);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/auth/role-group-management/{groupId}")
    public void createRoleGroupMapping(@PathVariable String groupId, @RequestBody DynamicHolder holder) throws Exception {

        if (null == holder.getGroupId() || holder.getRoleNames().isEmpty()) {
            throw new Exception("Group Id & Role Names are mandatory");
        }
        try {
            authAdminGroupService.saveRoleGroupMapping(holder);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/groups/{id}/roles")
    public ResponseEntity<List<RoleRepresentation>> getRolesFromGroup(@PathVariable String id, Pageable pageable) throws Exception {
        List<RoleRepresentation> roles;
        try {
            roles = authAdminGroupService.findAllRoles(pageable, id);
            return ResponseEntity.ok().body(roles);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth//groups-with-users")
    public ResponseEntity<List<GroupRepresentation>> getAllGroupsWithUsers(Pageable pageable) throws Exception {
        List<GroupRepresentation> groups;
        try {
            groups = authAdminGroupService.findAllGroupsWithUsersPresent(pageable);
            return ResponseEntity.ok().body(groups);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/auth/groups/type/{groupType}")
    public ResponseEntity<List<GroupRepresentation>> getAllGroupsByType(@PathVariable String groupType, Pageable pageable)
            throws Exception {
        List<GroupRepresentation> groups;
        try {
            groups = authAdminGroupService.findAllGroupsByType(groupType, pageable);
            return ResponseEntity.ok().body(groups);
        } catch (Exception e) {
            throw e;
        }
    }
}
