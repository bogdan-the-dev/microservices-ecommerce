package ro.bogdansoftware.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = """
      select t from Token t inner join ApplicationUser u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expiresAt > CURRENT_TIMESTAMP )\s
      """)
    List<Token> findAllValidTokensByUser(Long id);

    Optional<Token> findByToken(String token);

    @Query(value = """
        select t from Token t where (t.expiresAt > CURRENT_TIMESTAMP) and t.used = false and (t.tokenType = :type) and (t.token = :token) \s   
    """)
    Optional<Token> findValidToken(@Param("type")TokenType type, @Param("token") String token);

    @Query(value = """
        select t from Token t where (t.expiresAt > CURRENT_TIMESTAMP) and t.used = false and (t.tokenType = :type) and (t.user.email = :email) \s   
    """)
    Optional<Token> findValidationToken(@Param("type")TokenType type, @Param("email") String email);

}
