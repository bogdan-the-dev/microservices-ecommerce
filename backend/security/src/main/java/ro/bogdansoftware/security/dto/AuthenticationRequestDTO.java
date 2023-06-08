package ro.bogdansoftware.security.dto;


public record AuthenticationRequestDTO(
        String username,
        String password
) {
}
