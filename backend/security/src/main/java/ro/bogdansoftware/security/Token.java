package ro.bogdansoftware.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bogdansoftware.security.model.ApplicationUser;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private LocalDateTime expiresAt;
    private boolean used;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
