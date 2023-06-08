package ro.bogdansoftware.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.security.model.ApplicationUser;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final ITokenRepository tokenRepository;

    public String generateToken(ApplicationUser user, TokenType type) {
        String uniqueToken = UUID.randomUUID().toString();

        Token token = Token
                .builder()
                .tokenType(type)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .token(uniqueToken)
                .build();

        tokenRepository.save(token);

        return uniqueToken;
    }

    public ApplicationUser getTokenOwner(String token) {
        return tokenRepository.findByToken(token).orElseThrow().getUser();
    }

    public boolean verifyToken(TokenType type, String token) {
        var optionalToken = tokenRepository.findValidToken(type, token);
        if(optionalToken.isEmpty()) {
            return false;
        }
        var dbToken = optionalToken.get();
        dbToken.setUsed(true);
        tokenRepository.save(dbToken);
        return true;
    }

    public String resendVerificationCode(String email) {
        var token = tokenRepository.findValidationToken(TokenType.ACCOUNT_CONFIRMATION, email);
        if(token.isEmpty())
            return "";
        return token.get().getToken();
    }

    public ApplicationUser validatePasswordResetToken(String token) {
        var dbToken = tokenRepository.findValidToken(TokenType.PASSWORD_RESET, token);
        return dbToken.map(Token::getUser).orElse(null);
    }

    public void setTokenUsed(String token) {
        var dbToken = tokenRepository.findByToken(token).get();
        dbToken.setUsed(true);
        tokenRepository.save(dbToken);
    }

}
