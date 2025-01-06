package com.culture_ticket.client.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

  private final SecretKey secretKey;

  public LocalJwtAuthenticationFilter(@Value("${service.jwt.secret-key}") String secretKeyValue) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyValue));
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    if (path.equals("/api/v1/users/login") || path.equals("/api/v1/users/signup") ||
        path.matches("/api/v1/categories/info.*")
    ){
      return chain.filter(exchange);
    }

    String token = extractToken(exchange);

    try {
      if (token == null) {
        throw new Exception("Token is null");
      }
      exchange = validateToken(token, exchange);
    } catch (Exception exception) {
      handleTokenException(exception);
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    return chain.filter(exchange);
  }

  private void handleTokenException(Exception exception) {
    if (exception instanceof SecurityException || exception instanceof MalformedJwtException
        || exception instanceof SignatureException) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", exception);
    } else if (exception instanceof ExpiredJwtException) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.", exception);
    } else if (exception instanceof UnsupportedJwtException) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", exception);
    } else if (exception instanceof IllegalArgumentException) {
      log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.", exception);
    } else {
      log.error("An error occurred while validating the token.", exception);
    }
  }

  private String extractToken(ServerWebExchange exchange) {
    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }

  private ServerWebExchange validateToken(String token, ServerWebExchange exchange) {
    Jws<Claims> claimsJws = Jwts.parser()
        .verifyWith(secretKey)
        .build().parseSignedClaims(token);

    log.info("payload :: " + claimsJws.getPayload().toString());

    Claims claims = claimsJws.getPayload();

    // 새로운 exchange 객체를 반환
    return exchange.mutate().request(exchange.getRequest().mutate()
        .header("X-User-Id", claims.get("userId").toString())
        .header("X-User-Name", claims.get("username").toString())
        .header("X-User-Role", claims.get("role").toString())
        .build()).build();
  }
}