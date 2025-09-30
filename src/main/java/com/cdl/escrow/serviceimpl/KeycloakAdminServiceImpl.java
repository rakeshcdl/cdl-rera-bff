/**
 * KeycloakAdminServiceImpl.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 22/05/25
 */


package com.cdl.escrow.serviceimpl;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminServiceImpl {

     private final  Keycloak keycloak;

     private static final String REALM_NAME = "cdl_rera";

    @Transactional
    public String createUser(String username, String email, String password) {

        // Create password credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        // Build user representation
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(credential));
        String statusMessage = "";
        try {
            Response response = keycloak.realm(REALM_NAME).users().create(user);
            if (response.getStatus() == 201) {
                statusMessage =  "User created";
            } else {
                statusMessage =  "Failed: " + response.getStatus();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return statusMessage;
    }

    @Transactional(readOnly = true)
    public List<UserRepresentation> getAllUsers() {
     // UsersResource usersResource = keycloak.realm(realm).users();
      // return usersResource.list();  // fetches all users
      return keycloak.realm(REALM_NAME).users().list();
    }

}
