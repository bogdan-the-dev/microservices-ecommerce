package ro.bogdansoftware.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete");

    @Getter
    private final String permission;

    public static Permission fromValue(String value) {
        for(Permission p : Permission.values()) {
            if (p.permission.equals(value)) {
                return p;
            }
        }
        throw  new IllegalArgumentException("Invalid permission value: " + value);
    }
}
