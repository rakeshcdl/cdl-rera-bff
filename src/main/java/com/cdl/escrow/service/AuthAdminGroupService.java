/**
 * AuthAdminGroupService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */


package com.cdl.escrow.service;

import com.cdl.escrow.helper.DynamicHolder;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthAdminGroupService {

    int save(GroupRepresentation group) throws Exception;

    void updateGroup(GroupRepresentation group) throws Exception;

    Page<GroupRepresentation> findAll(Pageable pageable) throws Exception;

    List<GroupRepresentation> findAllGroup() throws Exception;

    GroupRepresentation findOne(String id) throws Exception;

    void delete(String id) throws Exception;

    void saveUserGroupMapping(DynamicHolder holder) throws Exception;

    void saveRoleGroupMapping(DynamicHolder holder) throws Exception;

    List<RoleRepresentation> findAllRoles(Pageable pageable, String groupId) throws Exception;

    List<GroupRepresentation> findAllGroupsWithUsersPresent(Pageable pageable) throws Exception;

    List<GroupRepresentation> findAllGroupsByType(String type, Pageable pageable) throws Exception;

    List<GroupRepresentation> findGroupWithRoles(String user_id) throws Exception;
}
