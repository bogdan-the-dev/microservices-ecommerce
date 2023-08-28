package ro.bogdansoftware.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor
public class AdminUser {
    private String username;
    private String email;
    private boolean isDisabled;

    public static AdminUser Convert(ApplicationUser user) {
        return AdminUser.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .isDisabled(user.isDisabled())
                .build();
    }

}
