package com.cdl.escrow.workflow.dto.ext;

import com.cdl.escrow.workflow.enume.RoleView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workflow-inbox")
@RequiredArgsConstructor
@Slf4j
public class WorkflowInboxController {

    private final WorkflowInboxService inboxService;


    // Example:
    // GET /api/v1/workflow/inbox/awaiting?module=payments&role=CHECKER
    // Roles are derived from JWT via SecurityContext
    @GetMapping("/awaiting")
    public org.springframework.http.ResponseEntity<org.springframework.data.domain.Page<AwaitingActionDTO>> awaiting(
            @RequestParam(required = true) String module,
            @RequestParam(defaultValue = "ALL") RoleView role,
            @ParameterObject Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        java.util.List<String> userAuthorities = new java.util.ArrayList<>();
        if (authentication != null) {
            // Add GrantedAuthorities (e.g., ROLE_MAKER)
            userAuthorities.addAll(authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());

            // Add Keycloak groups claim (e.g., PAYMENTS_MAKER) if present
            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                Object groupsClaim = jwtAuth.getToken().getClaim("groups");
                if (groupsClaim instanceof java.util.Collection<?> col) {
                    for (Object g : col) {
                        if (g != null) userAuthorities.add(g.toString());
                    }
                }
            }
        }

        var page = inboxService.awaitingForRole(module, role, userAuthorities, pageable);
        return org.springframework.http.ResponseEntity.ok(page);
    }
}
