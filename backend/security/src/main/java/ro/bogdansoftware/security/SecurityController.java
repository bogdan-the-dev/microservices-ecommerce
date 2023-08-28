package ro.bogdansoftware.security;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.security.dto.*;
import ro.bogdansoftware.security.model.AdminUser;
import ro.bogdansoftware.shared.security.InternalAuthResponse;
import ro.bogdansoftware.shared.security.SecurityURLS;

import java.util.List;

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
    public ResponseEntity<InternalAuthResponse> authorize(@RequestParam(value = "token")String token) {
        var role = service.getRole(token.substring(7));
        var username = service.getUsername(token.substring(7));
        return ResponseEntity.ok(new InternalAuthResponse(username, role));
        /*response.setHeader("auth-user-role", role);
        return ResponseEntity.ok().build();*/
    }

    @PostMapping(value = "security/register-admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody RegisterRequestDTO requestDTO) {
        service.createAdmin(requestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "security/enable")
    public ResponseEntity<Void> enableAdmin(@RequestBody List<String> username) {
        service.enable(username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "security/disable")
    public ResponseEntity<Void> disableAdmin(@RequestBody List<String> username) {
        service.disable(username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "security/delete")
    public ResponseEntity<Void> deleteAdmin(@RequestBody List<String> username) {
        service.deleteAdmin(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "security/get")
    public ResponseEntity<List<AdminUser>> get() {
        return ResponseEntity.ok(service.getAdmins());
    }
}
