/**
 * KeycloakAdminConfig.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 22/05/25
 */


package com.cdl.escrow.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {

        @Bean
        public Keycloak keycloakAdmin() {
            return KeycloakBuilder.builder()
                    .serverUrl("https://103.181.200.143:8443")
                    .realm("cdl_rera")
                    .clientId("webapiaccess")
                    .clientSecret("LnW8I0srfX3QYlILMkqeGQT81kPVkbFp")
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
    }

