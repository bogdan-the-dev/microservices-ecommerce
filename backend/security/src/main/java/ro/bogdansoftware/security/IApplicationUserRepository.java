package ro.bogdansoftware.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.bogdansoftware.security.model.ApplicationUser;
import ro.bogdansoftware.security.model.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface IApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsernameIs(String username);
    Optional<ApplicationUser> findByEmailIs(String email);

    List<ApplicationUser> findApplicationUsersByRoleIs(UserRole role);
}
