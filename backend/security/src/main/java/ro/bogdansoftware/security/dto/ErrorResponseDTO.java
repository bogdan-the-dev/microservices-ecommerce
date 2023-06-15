package ro.bogdansoftware.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private int error;
    private String message;
}
