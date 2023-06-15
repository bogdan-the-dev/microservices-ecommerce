package ro.bogdansoftware.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.bogdansoftware.security.dto.ErrorResponseDTO;
import ro.bogdansoftware.security.exception.AccountCreationException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyUsed(AccountCreationException ex) {
        var error = ErrorResponseDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidLoginCredentials(AccessDeniedException ex) {
        var error = ErrorResponseDTO.builder().message("Invalid login credentials.").build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountNotVerified() {
        var error = ErrorResponseDTO.builder().message("Account not verified.").build();
        return ResponseEntity.badRequest().body(error);
    }
}
