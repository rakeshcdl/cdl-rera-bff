/**
 * AuthAdminRoleServiceImpl.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */


package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.service.AuthAdminRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
