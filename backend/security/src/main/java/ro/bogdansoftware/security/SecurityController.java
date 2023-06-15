package ro.bogdansoftware.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.security.dto.*;
import ro.bogdansoftware.shared.security.SecurityURLS;

@RestController
@RequestMapping(SecurityURLS.SECURITY_BASE)
@AllArgsConstructor
public class SecurityController {
    private final SecurityService service;

    @PostMapping(value = "auth/register")
    private ResponseEntity<Boolean> register(@RequestBody RegisterRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(service.register(requestDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(value = "http://localhost:4200")
    @PostMapping(value = "auth/authenticate")
    private ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationDTO) {
        return ResponseEntity.ok(service.authenticate(authenticationDTO));
    }

    @GetMapping(value = "auth/verify-account")
    private ResponseEntity<Boolean> activateAccount(@RequestParam(name = "token") String token) {
        return ResponseEntity.ok(service.verifyAccount(token));
    }

    @GetMapping(value = "auth/resend-account-verification-code")
    private ResponseEntity<Void> resendAccountVerificationCode(@RequestParam(name = "email")String email) {
        service.resendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "auth/send-reset-code")
    private ResponseEntity<Void> sendResetCode(@RequestParam(name ="email")String email) {
        service.sendResetPasswordToken(email);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "auth/get-user-data")
    private ResponseEntity<UserDataDTO> getUserData(@RequestParam(name = "token")String token) {
        return ResponseEntity.ok(UserDataDTO.builder()
                .username(service.getUsername(token))
                .role(service.getRole(token))
                .build());
    }
    @PutMapping(value = "auth/reset-password")
    private ResponseEntity<Boolean> resetPassword(
            @RequestParam(name = "token") String token,
            @RequestBody ResetPasswordRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.resetPassword(token, requestDTO.password()));
    }

    @GetMapping(value = SecurityURLS.AUTHORIZE)
    public ResponseEntity<String> authorize(@RequestParam(value = "token")String token) {
        var role = service.getRole(token.substring(7));
        return ResponseEntity.ok(role);
        /*response.setHeader("auth-user-role", role);
        return ResponseEntity.ok().build();*/
    }
}
