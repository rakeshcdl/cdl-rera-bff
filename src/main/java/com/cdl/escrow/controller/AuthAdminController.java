

package com.cdl.escrow.controller;

import com.cdl.escrow.service.AuthAdminGroupService;
import com.cdl.escrow.serviceimpl.KeycloakAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AuthAdminController {

    private final KeycloakAdminServiceImpl keycloakAdminService;

    private final AuthAdminGroupService authAdminGroupService;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestParam String username,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        String result = keycloakAdminService.createUser(username, email, password);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public List<UserRepresentation> getUsers() {
        return keycloakAdminService.getAllUsers();
    }
}
