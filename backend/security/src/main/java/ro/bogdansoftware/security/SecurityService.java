package ro.bogdansoftware.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.amqp.RabbitMQMessageProducer;
import ro.bogdansoftware.clients.notification.NotificationType;
import ro.bogdansoftware.clients.notification.SendNotificationRequest;
import ro.bogdansoftware.security.dto.AuthenticationRequestDTO;
import ro.bogdansoftware.security.dto.AuthenticationResponseDTO;
import ro.bogdansoftware.security.dto.RegisterRequestDTO;
import ro.bogdansoftware.security.exception.AccountCreationException;
import ro.bogdansoftware.security.model.AdminUser;
import ro.bogdansoftware.security.model.ApplicationUser;
import ro.bogdansoftware.security.model.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final IApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final UserDetailsService detailsService;

    public void createAdmin(RegisterRequestDTO requestDTO) {
        var user = ApplicationUser.builder()
                .email(requestDTO.email())
                .passwordHash(passwordEncoder.encode(requestDTO.password()))
                .role(UserRole.ADMIN)
                .username(requestDTO.username())
                .confirmed(true)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    public boolean register(RegisterRequestDTO requestDTO) {
        if (!EmailValidator.isValidEmail(requestDTO.email())) {
           throw new AccountCreationException("Invalid email.");
        }

        if(userRepository.findByUsernameIs(requestDTO.username()).isPresent()) {
            throw new AccountCreationException("Username already used.");
        }

        if(userRepository.findByEmailIs(requestDTO.email()).isPresent()) {
            throw new AccountCreationException("Email already used");
        }

        var user = ApplicationUser.builder()
                .email(requestDTO.email())
                .username(requestDTO.username())
                .passwordHash(passwordEncoder.encode(requestDTO.password()))
                .role(UserRole.USER)
                .confirmed(false)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        var token = tokenService.generateToken(user, TokenType.ACCOUNT_CONFIRMATION);

        SendNotificationRequest notificationRequest = new SendNotificationRequest(token, NotificationType.CONFIRMATION_EMAIL, requestDTO.username(), "Account confirmation", requestDTO.email());

        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");

        return true;
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationDTO) {
        var token = new UsernamePasswordAuthenticationToken(
                authenticationDTO.username(),
                authenticationDTO.password());

        authenticationManager.authenticate(token);

        var user = this.userRepository.findByUsernameIs(authenticationDTO.username()).orElseThrow();

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder().accessToken(jwt).role(user.getRole().name()).build();

    }

    public ApplicationUser getUser(String username) {
        return userRepository.findByUsernameIs(username).orElseThrow();
    }

    public void resendVerificationCode(String email) {
        var user = userRepository.findByEmailIs(email).orElseThrow();
        if (user.isConfirmed()) {
            return;
        }
        var token = tokenService.resendVerificationCode(email);
        if(token.isEmpty()) {
            token = tokenService.generateToken(user, TokenType.ACCOUNT_CONFIRMATION);
        }
            rabbitMQMessageProducer.publish(new SendNotificationRequest(token, NotificationType.CONFIRMATION_EMAIL, user.getUsername(),"AccountConfirmation", user.getEmail()), "internal.exchange", "internal.notification.routing-key");
    }

    public boolean verifyAccount(String token) {
        var isTokenValid = tokenService.verifyToken(TokenType.ACCOUNT_CONFIRMATION, token);
        if(!isTokenValid) {
            return false;
        }
        var user = tokenService.getTokenOwner(token);
        user.setConfirmed(true);
        userRepository.save(user);

        return true;
    }

    public void sendResetPasswordToken(String email) {
        var user = userRepository.findByEmailIs(email);
        if(user.isPresent() && isUserAllowed(user.get())) {
            var token = tokenService.generateToken(user.get(), TokenType.PASSWORD_RESET);
            var notificationRequest = new SendNotificationRequest(token, NotificationType.PASSWORD_RESET, user.get().getUsername(), "Password reset",email);
            rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");
        }
    }

    public boolean resetPassword(String token, String password) {
        var user = tokenService.validatePasswordResetToken(token);
        if(user != null && isUserAllowed(user)) {
            user.setPasswordHash(passwordEncoder.encode(password));
            userRepository.save(user);

            var notificationReq = new SendNotificationRequest("You're password was changed", NotificationType.INFO_EMAIL, user.getUsername(), "Password changed", user.getEmail());
            rabbitMQMessageProducer.publish(notificationReq, "internal.exchange", "internal.notification.routing-key");
            tokenService.setTokenUsed(token);
            return true;
        }
        return false;
    }

    public String getRole(String jwtToken) {
        var email = jwtService.extractEmail(jwtToken);
        var authorities = detailsService.loadUserByUsername(email).getAuthorities();
        UserRole role = UserRole.getUSerRole(String.valueOf(authorities.stream().toList().get(0)));
        return role.name();
    }

    public List<AdminUser> getAdmins() {
        return userRepository.findApplicationUsersByRoleIs(UserRole.ADMIN).stream().map(AdminUser::Convert).collect(Collectors.toList());
    }

    public void disable(List<String> usernames) {
        for(String username: usernames) {
            var user = userRepository.findByUsernameIs(username).orElseThrow();
            user.setDisabled(true);
            userRepository.save(user);
        }
    }

    public void enable(List<String> usernames) {
        for(String username: usernames) {
            var user = userRepository.findByUsernameIs(username).orElseThrow();
            user.setDisabled(false);
            userRepository.save(user);
        }
    }

    public void deleteAdmin(List<String> usernames) {
        for(String username: usernames) {
            var user = userRepository.findByUsernameIs(username).orElseThrow();
            userRepository.delete(user);
        }
    }

    public boolean userExists(String email) {
        var userOptional = userRepository.findByEmailIs(email);
        return userOptional.isPresent();
    }

    public String getUsername(String jwtToken) {
        return jwtService.extractEmail(jwtToken);
    }

    private boolean isUserAllowed(ApplicationUser user) {
        return user.isConfirmed() && !user.isDisabled();
    }
}
