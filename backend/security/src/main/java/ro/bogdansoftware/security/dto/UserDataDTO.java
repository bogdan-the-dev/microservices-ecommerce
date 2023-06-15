package ro.bogdansoftware.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDataDTO {
    private String role;
    private String username;
}
