/**
 * AuthAdminGroupServiceImpl.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */


package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.helper.DynamicHolder;
import com.cdl.escrow.service.AuthAdminGroupService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.AbstractUserRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthAdminGroupServiceImpl implements AuthAdminGroupService {

    private final Keycloak keycloak;

    private static final String REALM_NAME = "cdl_rera";

    @Override
    @Transactional
    public int save(GroupRepresentation group) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        GroupsResource groupResource = realm.groups();
        try {
            Response response = groupResource.add(group);

            return response.getStatus();
        } catch (Exception e) {
            return 491;
        }
    }

    @Override
    @Transactional
    public void updateGroup(GroupRepresentation group) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        GroupsResource groupResource = realm.groups();
        try {
            groupResource.group(group.getId()).update(group);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupRepresentation> findAll(Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        List<GroupRepresentation> entireGroups = realm.groups().groups();
        if (!entireGroups.isEmpty()) {
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), entireGroups.size());
            final Page<GroupRepresentation> paginatedGroups = new PageImpl<>(entireGroups.subList(start, end), pageable,
                    entireGroups.size());

            paginatedGroups.getContent().forEach(item -> {
                GroupRepresentation currentGroup = realm.groups().group(item.getId()).toRepresentation();
                item.setAttributes(currentGroup.getAttributes());
                item.setClientRoles(currentGroup.getClientRoles());
                item.setRealmRoles(currentGroup.getRealmRoles());
            });
            return paginatedGroups;
        } else {
            throw new Exception("No Groups Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupRepresentation> findAllGroup() throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);

        List<GroupRepresentation> entireGroups = realm.groups().groups();
        if (!entireGroups.isEmpty()) {
            return entireGroups;
        } else {
            throw new Exception("No Groups Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GroupRepresentation findOne(String id) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        GroupsResource groupResource = realm.groups();
        GroupResource group = groupResource.group(id);
        if (null != group) {
            return group.toRepresentation();
        } else {
            throw new Exception("No Group Found with id : " + id);
        }
    }

    @Override
    @Transactional
    public void delete(String id) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        GroupsResource groupResource = realm.groups();
        if (null != groupResource.group(id)) {
            groupResource.group(id).remove();
        } else {
            throw new Exception("No Group Found with Id :" + id);
        }
    }

    @Override
    @Transactional
    public void saveUserGroupMapping(DynamicHolder holder) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UsersResource userResource = realm.users();
        GroupsResource group = realm.groups();
        List<UserRepresentation> users = group.group(holder.getGroupId()).members();

        if (!holder.getUserIds().isEmpty()) {
            try {
                List<String> ids = users.stream().map(AbstractUserRepresentation::getId).toList();

                Stream<String> holderMissing = holder.getUserIds().stream().filter(holderId -> !ids.contains(holderId));
                Stream<String> userMissing = ids.stream().filter(item -> !holder.getUserIds().contains(item));

                holderMissing.forEach(item -> {
                    userResource.get(item).joinGroup(holder.getGroupId());
                });

                userMissing.forEach(item -> {
                    userResource.get(item).leaveGroup(holder.getGroupId());
                });
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new Exception("Either Users and/or Group not Passed For User-Group Mappings");
        }
    }

    @Override
    @Transactional
    public void saveRoleGroupMapping(DynamicHolder holder) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        RolesResource roles = realm.roles();
        GroupsResource group = realm.groups();

        List<RoleRepresentation> rolesToAdd = new ArrayList<>();
        List<RoleRepresentation> rolesToRemove = new ArrayList<>();
        List<String> tempRoles = new ArrayList<>();

        List<RoleRepresentation> rolesAvailable = group.group(holder.getGroupId()).roles().realmLevel().listAll();
        if (null != rolesAvailable && !rolesAvailable.isEmpty()) {
            tempRoles = rolesAvailable.stream()
                    .filter(item -> !(item.getName().equalsIgnoreCase("offline_access"))
                            && !(item.getName().equalsIgnoreCase("uma_authorization")))
                    .map(RoleRepresentation::getName).toList();
        }
        List<String> defaultRolesRemoved = tempRoles;

        if (null != holder.getGroupId() && !holder.getRoleNames().isEmpty()) {
            try {
                Stream<String> holderMissing = holder.getRoleNames().stream()
                        .filter(holderName -> !defaultRolesRemoved.contains(holderName));

                Stream<String> rolesMappingAbsent = defaultRolesRemoved.stream()
                        .filter(item -> !holder.getRoleNames().contains(item));

                holderMissing.forEach(item -> {
                    RoleResource currentRole = roles.get(item);
                    rolesToAdd.add(currentRole.toRepresentation());
                });

                rolesMappingAbsent.forEach(item -> {
                    RoleResource currentRole = roles.get(item);
                    rolesToRemove.add(currentRole.toRepresentation());
                });

                group.group(holder.getGroupId()).roles().realmLevel().add(rolesToAdd); // working code
                group.group(holder.getGroupId()).roles().realmLevel().remove(rolesToRemove); // working code
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new Exception("Either Users and/or Group not Passed For User-Group Mappings");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleRepresentation> findAllRoles(Pageable pageable, String groupId) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        List<RoleRepresentation> rolesOfGroup;
        try {
            GroupsResource group = realm.groups();
            rolesOfGroup = group.group(groupId).roles().realmLevel().listAll();
            if (!rolesOfGroup.isEmpty()) {
                final int start = (int) pageable.getOffset();
                final int end = Math.min((start + pageable.getPageSize()), rolesOfGroup.size());
                final Page<RoleRepresentation> page = new PageImpl<>(rolesOfGroup.subList(start, end), pageable,
                        rolesOfGroup.size());
                return page.getContent();
            } else {
                throw new Exception("No Users Found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupRepresentation> findAllGroupsWithUsersPresent(Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        List<GroupRepresentation> entireGroups = realm.groups().groups();
        List<GroupRepresentation> finalisedGroups;
        if (!entireGroups.isEmpty()) {
            finalisedGroups = entireGroups.stream()
                    .filter(group -> !realm.groups().group(group.getId()).members().isEmpty())
                    .collect(Collectors.toList());
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), finalisedGroups.size());
            final Page<GroupRepresentation> paginatedGroups = new PageImpl<>(finalisedGroups.subList(start, end),
                    pageable, finalisedGroups.size());

            paginatedGroups.getContent().forEach(item -> {
                GroupRepresentation currentGroup = realm.groups().group(item.getId()).toRepresentation();
                item.setAttributes(currentGroup.getAttributes());
                item.setClientRoles(currentGroup.getClientRoles());
                item.setRealmRoles(currentGroup.getRealmRoles());
            });
            return paginatedGroups.getContent();
        } else {
            throw new Exception("No Groups Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupRepresentation> findAllGroupsByType(String type, Pageable pageable) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        List<GroupRepresentation> entireGroups = realm.groups().groups();
        List<GroupRepresentation> finalisedGroups;
        if (!entireGroups.isEmpty()) {
            finalisedGroups = entireGroups.stream().filter(
                            group -> null != realm.groups().group(group.getId()).toRepresentation().getAttributes().get("type")
                                    && realm.groups().group(group.getId()).toRepresentation().getAttributes().get("type").getFirst()
                                    .equalsIgnoreCase(type))
                    .collect(Collectors.toList());
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), finalisedGroups.size());
            final Page<GroupRepresentation> paginatedGroups = new PageImpl<>(finalisedGroups.subList(start, end),
                    pageable, finalisedGroups.size());

            paginatedGroups.getContent().forEach(item -> {
                GroupRepresentation currentGroup = realm.groups().group(item.getId()).toRepresentation();
                item.setAttributes(currentGroup.getAttributes());
                item.setClientRoles(currentGroup.getClientRoles());
                item.setRealmRoles(currentGroup.getRealmRoles());
            });
            return paginatedGroups.getContent();
        } else {
            throw new Exception("No Groups Found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupRepresentation> findGroupWithRoles(String userId) throws Exception {
        RealmResource realm = keycloak.realm(REALM_NAME);
        UsersResource userResource = realm.users();
        UserResource user = userResource.get(userId);
        if (null != user) {
            List<GroupRepresentation> groupMapping = user.groups();

            if (!groupMapping.isEmpty()) {
                groupMapping.forEach(item ->{
                    GroupRepresentation currentGroup = realm.groups().group(item.getId()).toRepresentation();
                    item.setAttributes(currentGroup.getAttributes());
                    item.setClientRoles(currentGroup.getClientRoles());
                    item.setRealmRoles(currentGroup.getRealmRoles());
                });
                return groupMapping;
            }else {
                throw new Exception("No Groups Found For User");
            }
        }else {
            throw new Exception("No User Found With Id :" + userId);
        }

    }
}
