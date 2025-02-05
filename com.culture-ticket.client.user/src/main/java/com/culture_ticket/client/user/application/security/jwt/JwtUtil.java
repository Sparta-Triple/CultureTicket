package com.culture_ticket.client.user.application.security.jwt;

import com.culture_ticket.client.user.domain.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  private final String issuer;
  private final long EXPIRATION_TIME;
  private final SecretKey secretKey;

  public JwtUtil(
      @Value("${service.jwt.secret-key}") String secretKeyValue,
      @Value("${spring.application.name}") String issuer,
      @Value("${service.jwt.access-expiration}") long EXPIRATION_TIME
  ) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyValue));
    this.issuer = issuer;
    this.EXPIRATION_TIME = EXPIRATION_TIME;
  }

  // 토큰 생성
  public String createToken(Long userId, String username, String nickname, Role role) {
    return BEARER_PREFIX + Jwts.builder()
        .subject(username)
        .issuer(issuer)
        .claim("userId", userId)
        .claim("username", username)
        .claim("nickname", nickname)
        .claim("role", role)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  //토큰 가져오기 헤더
  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length()).trim();
    }
    return null;
  }

  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }
}