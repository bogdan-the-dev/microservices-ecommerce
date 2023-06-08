package ro.bogdansoftware.clients.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ro.bogdansoftware.shared.security.SecurityURLS;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "security",
        path = SecurityURLS.SECURITY_BASE
)
public interface ISecurityClient {
    @GetMapping(value = SecurityURLS.AUTHORIZE)
    ResponseEntity<String> authorize(String token);
}
