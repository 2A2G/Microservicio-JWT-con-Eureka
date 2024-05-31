package microServicio.JWT.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import microServicio.JWT.Entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Component
@Slf4j
public class JWT_Utils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    //    Lista negra de token
    private final Set<String> blacklistedTokens = new HashSet<>();

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    //    Obteniendo firma del token
    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //    Generando el token
    public String generateToken(Usuario usuario) {

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + Long.parseLong(timeExpiration));

        return Jwts.builder().subject(usuario.getUsername()).issuedAt(currentDate).expiration(expirationDate)
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    //    Validando el token
    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build().parseSignedClaims(token).getPayload();

            boolean isBlacklisted = blacklistedTokens.contains(token);
            if (isBlacklisted) {
                log.error("Token invalido, token en blacklist");
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("Error al validar el token: " + e.getMessage());
            return false;
        }
    }


    public <T> T getClains(String token, Function<Claims, T> clainsResolver) {
        Claims claims = extraAllClaims(token);
        return clainsResolver.apply(claims);
    }

    public Claims extraAllClaims(String Token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build().parseSignedClaims(Token).getPayload();
    }

    //    Obteniendo el usuario del token
    public String getUsernameFromToken(String token) {
        return getClains(token, Claims::getSubject);
    }


}
