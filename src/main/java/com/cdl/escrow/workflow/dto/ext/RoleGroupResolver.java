package com.cdl.escrow.workflow.dto.ext;

import com.cdl.escrow.workflow.enume.RoleView;

public final class RoleGroupResolver {

    private RoleGroupResolver(){}

    /** Map a high-level role (ROLE_MAKER) to stage group(s) like PAYMENTS_MAKER. */
    public static java.util.Collection<String> resolveGroupsForRole(
            String moduleName,
            RoleView role,
            java.util.Collection<String> userAuthorities // e.g. ["ROLE_MAKER","ROLE_CHECKER"]
    ) {
        if (role == RoleView.ALL || role == RoleView.ADMIN) {
            // Keep it strict to module-scoped groups from authorities if you prefix them (optional).
            // Or simply return all module-* groups if you have them:
            String prefix = moduleName == null ? "" : moduleName.trim().toUpperCase() + "_";
            return userAuthorities.stream()
                    .map(a -> normalizeAuthority(a))
                    .filter(a -> a.startsWith(prefix) || a.startsWith("ROLE_"))
                    .collect(java.util.stream.Collectors.toSet());
        }

        String normalizedModule = moduleName == null ? "" : moduleName.trim().toUpperCase();
        String expectedGroup = switch (role) {
            case MAKER     -> normalizedModule + "_MAKER";
            case CHECKER   -> normalizedModule + "_CHECKER";
            case APPROVER  -> normalizedModule + "_APPROVER";
            case REVIEWER  -> normalizedModule + "_REVIEWER";
            default        -> "";
        };

        boolean userHasRole = userAuthorities.stream()
                .map(a -> normalizeAuthority(a))
                .anyMatch(a -> a.equals("ROLE_" + role.name())); // from Keycloak token (with or without ROLE_ prefix)

        if (!userHasRole) {
            // If user doesn't explicitly have ROLE_<ROLE>, allow if they belong to module group path
            // Normalize Keycloak group paths "/MODULE/MAKER" -> "MODULE_MAKER"
            boolean inExpectedGroup = userAuthorities.stream()
                    .map(a -> normalizeAuthority(a))
                    .anyMatch(a -> a.equals(expectedGroup));
            if (inExpectedGroup && !expectedGroup.isBlank()) {
                return java.util.List.of(expectedGroup);
            }
            return java.util.List.of();
        }

        return !expectedGroup.isBlank()
                ? java.util.List.of(expectedGroup)
                : java.util.List.of();
    }

    private static String normalizeAuthority(String raw) {
        if (raw == null) return "";
        String up = raw.trim().toUpperCase();
        // Convert Keycloak group path to module_group format
        if (up.startsWith("/")) {
            up = up.replace('/', '_'); // "/PAYMENTS/MAKER" -> "_PAYMENTS_MAKER"
            if (up.startsWith("_")) up = up.substring(1);
        }
        // Ensure ROLE_ prefix presence for roles without it
        if (!up.startsWith("ROLE_") && (up.equals("MAKER") || up.equals("CHECKER") || up.equals("APPROVER") || up.equals("REVIEWER") || up.equals("ADMIN"))) {
            up = "ROLE_" + up;
        }
        return up;
    }
}
