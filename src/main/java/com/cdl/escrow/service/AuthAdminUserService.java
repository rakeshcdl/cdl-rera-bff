

package com.cdl.escrow.service;

import com.cdl.escrow.helper.AuthUsersWithRoles;
import org.keycloak.representations.idm.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AuthAdminUserService {

    Page<UserRepresentation> findAllUsers(Pageable pageable, String groupId) throws Exception;

    List<UserRepresentation> findAllStaffUsers(String groupId) throws Exception;

    List<UserRepresentation> findAllStaffUsersEntity(String groupId,String entity) throws Exception;

    Page<UserRepresentation> findAll(Pageable pageable) throws Exception;

    List<UserRepresentation> findAllUsers() throws Exception;

    int save(UserRepresentation user) throws Exception;

    void updateUser(UserRepresentation user) throws Exception;

    UserRepresentation findOne(String id) throws Exception;

    UserRepresentation findOneEntity(String id,String entity) throws Exception;

    void delete(String id) throws Exception;

    void softDeleteUser(UserRepresentation user) throws Exception;

    Page<GroupRepresentation> findGroupMappingOfUser(String userId, Pageable pageable) throws Exception;

    List<RoleRepresentation>  findOneRole(String id) throws Exception;

    List<AuthUsersWithRoles>  findAllRoles() throws Exception;

    Page<AdminEventRepresentation> findAllAdminEvents(String realm, Pageable pageable) throws Exception;

    Page<EventRepresentation> findAllUserEvents(String realm, Pageable pageable) throws Exception;

    UserSessionRepresentation findUserSession(String id) throws Exception;

    void assignRolesToUser(String userId, List<String> roles);

    void assignGroupsToUser(String userId, List<String> groups);

    void removeRolesFromUser(String userId, List<String> roles);

    void removeGroupsFromUser(String userId, List<String> groups);

    List<Map<String, Object>> getAllUsersWithRolesAndGroups();

    Map<String, Object> getUserRolesAndGroups(String userId);
}
