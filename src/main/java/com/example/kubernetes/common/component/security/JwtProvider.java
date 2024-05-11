package com.example.kubernetes.common.component.security;

import com.example.kubernetes.user.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Log4j2
@Component
public class JwtProvider {
    @Value("${jwt.iss}")
    private String issuer;

    private final SecretKey secretKey;
    Instant expiredDate = Instant.now().plus(1, ChronoUnit.DAYS);

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createToken(UserDto dto) {
        String token = Jwts.builder()
                .issuer(issuer)
                .signWith(secretKey)
                .expiration(Date.from(expiredDate))
                .subject("rod")
                .claim("userId", dto.getId())
                .claim("username", dto.getUsername())
                .claim("job", dto.getJob())
                .compact();
        log.info("로그인 성공으로 발급된 토큰: " + token);
        return token;
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        log.info("Request getServletPath value from frontend : {}", request.getServletPath());
        String bearerToken = request.getHeader("Authorization");
        log.info("Token from frontend : {}", bearerToken);
        return bearerToken != null && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : "undefined";
    }

    public void printPayload(String accessToken) {
        //토큰을 각 세션(header, payload, signature)으로 분할
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        log.info("JwtProvider Access Token header: " + header);
        log.info("JwtProvider Access Token payload: " + payload);
    }

    public Claims getPayload(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        //secretKey는 provider에서만 있어야 함
    }


}
