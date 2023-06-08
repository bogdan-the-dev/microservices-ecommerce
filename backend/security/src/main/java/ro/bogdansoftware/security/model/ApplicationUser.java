package ro.bogdansoftware.security.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

@Table(name = "application_user")
@Entity
@Builder
@Data
@AllArgsConstructor
public class ApplicationUser implements UserDetails {

    public ApplicationUser() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "username")
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "confirmed")
    private boolean confirmed;
    @Column(name = "disabled")
    private boolean disabled;
    @Column(name = "twoFA")
    private boolean twoFAEnabled;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return confirmed;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
