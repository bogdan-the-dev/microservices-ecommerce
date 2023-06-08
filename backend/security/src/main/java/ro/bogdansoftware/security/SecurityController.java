package ro.bogdansoftware.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.security.dto.AuthenticationRequestDTO;
import ro.bogdansoftware.security.dto.AuthenticationResponseDTO;
import ro.bogdansoftware.security.dto.RegisterRequestDTO;
import ro.bogdansoftware.security.dto.ResetPasswordRequestDTO;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class SecurityController {
    private final SecurityService service;

    @PostMapping(value = "register")
    private ResponseEntity<Boolean> register(@RequestBody RegisterRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(service.register(requestDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "authenticate")
    private ResponseEntity<AuthenticationResponseDTO> register(@RequestBody AuthenticationRequestDTO authenticationDTO) {
        return ResponseEntity.ok(service.authenticate(authenticationDTO));
    }

    @GetMapping(value = "verify-account")
    private ResponseEntity<Boolean> activateAccount(@RequestParam(name = "token") String token) {
        return ResponseEntity.ok(service.verifyAccount(token));
    }

    @GetMapping(value = "resend-account-verification-code")
    private ResponseEntity<Void> resendAccountVerificationCode(@RequestParam(name = "email")String email) {
        service.resendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "send-reset-code")
    private ResponseEntity<Void> sendResetCode(@RequestParam(name ="email")String email) {
        service.sendResetPasswordToken(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "reset-password")
    private ResponseEntity<Boolean> resetPassword(
            @RequestParam(name = "token") String token,
            @RequestBody ResetPasswordRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.resetPassword(token, requestDTO.password()));
    }

    @GetMapping(value = "authorize")
    private ResponseEntity<Void> authorize(HttpServletRequest request, HttpServletResponse response) {
        var role = service.getRole(request.getHeader("Authorization").substring(7));
        response.setHeader("auth-user-role", role);
        return ResponseEntity.ok().build();
    }
}
