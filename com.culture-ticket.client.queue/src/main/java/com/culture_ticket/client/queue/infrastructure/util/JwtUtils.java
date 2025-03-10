package com.culture_ticket.client.queue.infrastructure.util;

import com.culture_ticket.client.queue.common.CustomException;
import com.culture_ticket.client.queue.common.ErrorType;
import com.culture_ticket.client.queue.domain.repository.RedisRepository;
import com.culture_ticket.client.queue.domain.repository.WaitingQueueRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret-key}")
    public String SECRET_KEY;

    // 토큰 유효시간 1시간
    public static final long EXP_TIME = 60 * 60 * 1000L;

    private final WaitingQueueRepository waitingQueueRepository;
    private final RedisRepository redisRepository;

    /**
     * 토큰을 생성한다.
     *
     * @param sessionId sessionId 정보
     * @return String token 정보
     */
    public String createToken(String sessionId) {

        return Jwts
                .builder()
                .setSubject(sessionId) // JWT payload 에 저장되는 정보
                .setIssuedAt(new Date(System.currentTimeMillis())) //발행 일자
                .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME)) // set Expire Time
                .signWith(this.getSigningKey())  // 사용할 암호화 알고리즘과 secret key 세팅
                .compact();
    }

    /**
     * JWT 토큰 정보를 해독한다.
     *
     * @param token JWT token 정보
     * @return Long userId 정보
     */
    @Transactional(noRollbackFor = CustomException.class)
    public Long resolveToken(String token) {
        // 토큰 추출
        if (!StringUtils.hasText(token)) {
            throw new CustomException(ErrorType.TOKEN_IS_NOT_FOUND);
        }
        // 토큰 파싱
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.getSigningKey())
                    .build()
                    .parseClaimsJws(token);

        } catch (ExpiredJwtException e) {
            changeTokenStatus(token);
            log.error("토큰이 만료되었습니다. {}", e.getMessage());
            throw new CustomException(ErrorType.TOKEN_IS_EXPIRED);
        } catch (Exception e) {
            log.error("토큰 파싱 중 오류가 발생하였습니다. {}", e.getMessage());
            throw new CustomException(ErrorType.INVALID_TOKEN);
        }

        log.debug("claims.getBody : {}", claims.getBody());
        log.debug("claims.getHeader : {}", claims.getHeader());

        // userId 추출
        String subject = claims.getBody().getSubject();
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException e) {
            log.error("userId 변환 중 오류가 발생히였습니다. {}", e.getMessage());
            throw new CustomException(ErrorType.INVALID_TOKEN_PAYLOAD);
        }
    }

    /**
     * db에 있는 토큰 상태를 만료로 변경한다.
     *
     * @param token JWT 토큰 정보
     */
    private void changeTokenStatus(String token) {
        waitingQueueRepository.deleteExpiredToken(token);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
