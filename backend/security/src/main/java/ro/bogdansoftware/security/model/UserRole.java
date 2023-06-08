package ro.bogdansoftware.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ro.bogdansoftware.security.model.Permission.*;

@RequiredArgsConstructor
public enum UserRole {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public static UserRole getUserRoleFromAuthorities(List<SimpleGrantedAuthority> authorities) {
        var permissionSet = authorities.stream().map(auth -> auth.getAuthority().substring(5)).map(Permission::fromValue).collect(Collectors.toSet());
        for (UserRole r : UserRole.values()) {
            if(r.permissions.equals(permissionSet)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid list of authorities");
    }

    public static UserRole getUSerRole(String role) {
        String roleString = role.substring(5);
        for(UserRole r: UserRole.values()) {
            if(r.name().equals(roleString)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid role name provided");
    }
}
