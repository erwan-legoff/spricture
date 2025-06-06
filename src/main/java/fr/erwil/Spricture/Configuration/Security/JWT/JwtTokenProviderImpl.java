package fr.erwil.Spricture.Configuration.Security.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProviderImpl implements IJwtTokenProvider {

    private final long loginExpirationMilliseconds;
    private final long verifyExpirationMilliseconds;
    private final SecretKey secretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProviderImpl.class);

    public JwtTokenProviderImpl(SecurityJwtProperties properties) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecretKey()));
        this.loginExpirationMilliseconds = properties.getLoginExpirationMilliseconds();
        this.verifyExpirationMilliseconds = properties.getValidateExpirationMilliseconds();

    }

    /**
     * Will generate token based on a username and the current date.
     * @param username the users name
     * @return the token
     */
    @Override
    public String generateLoginToken(String username) {
        return generateToken(username,this.loginExpirationMilliseconds);
    }

    @Override
    public String generateVerifyToken(String username) {
        return generateToken(username,this.verifyExpirationMilliseconds);
    }


    public String generateToken(String username, long expirationMilliseconds) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expirationMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(this.secretKey, Jwts.SIG.HS256)
                .compact();
    }


    /**
     * Extract the username from the token
     * @param token the token to extract from
     * @return the name
     */
    @Override
    public String extractUserName(String token) {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Will verify is the token didn't expire, or is malformed, invalid or empty
     * @param token the token to validate
     * @return true if valid, false if something went wrong with the token
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parse(token);

            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token has expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Token is malformed: {}", e.getMessage());
        } catch (SecurityException e) {
            logger.warn("Token signature is invalid: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Token is null, empty, or only whitespace: {}", e.getMessage());
        }

        return false;
    }

}
