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
import ro.bogdansoftware.security.model.ApplicationUser;
import ro.bogdansoftware.security.model.UserRole;

import java.time.LocalDateTime;
import java.util.List;

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

    public boolean register(RegisterRequestDTO requestDTO) throws Exception {
        if (!EmailValidator.isValidEmail(requestDTO.email())) {
           throw new Exception("Invalid email");
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
        //todo send token via email

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
        return AuthenticationResponseDTO.builder().accessToken(jwt).build();

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
        UserRole role = UserRole.getUserRoleFromAuthorities((List<SimpleGrantedAuthority>) authorities);
        return role.name();
    }

    private boolean isUserAllowed(ApplicationUser user) {
        return user.isConfirmed() && !user.isDisabled();
    }
}
