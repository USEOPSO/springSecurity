package com.example.springsecurity.global.jwt;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.springsecurity.user.entities.User;
import com.example.springsecurity.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
// @RequiredArgsConstructor
public class TokenProvider {
	private static final long ACCESS_TOKEN_EXPIRED =  1000L * 60 * 2; // 2분
	private final Key jwtSecretKey;
	private final UserRepository userRepository;

	public TokenProvider(@Value("${jwt.secret}") String secretKey, UserRepository userRepository) {
		this.userRepository = userRepository;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		jwtSecretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateJWT(final User user) {
		return Jwts.builder()
			.setSubject("authorization")
			.claim("userId", user.getUserId()) // name claim 설정
			.claim("role", user.getRole()) // email claim 설정
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED))
			.signWith(jwtSecretKey,SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(final String token) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			System.out.println("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			System.out.println("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			System.out.println("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			System.out.println("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	public Claims parseClaims(final String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public Authentication getAuthentication(final String token) {
		// 토큰 복호화
		Claims claims = parseClaims(token);
		System.out.println("token_claims : " + claims.toString());

		if (claims.get("role") == null) {
			throw new BadCredentialsException("권한 정보가 없는 토큰입니다.");
		}

		// 클레임에서 권한 정보 가져오기
		final Collection<? extends GrantedAuthority> authorities = Stream.of(
				claims.get("role").toString())
			.map(SimpleGrantedAuthority::new)
			.toList();

		Long userId = Long.valueOf(claims.get("userId").toString());

		//token 에 담긴 정보에 맵핑되는 User 정보 디비에서 조회
		final User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not Found User"));

		//Authentication 객체 생성
		return new UsernamePasswordAuthenticationToken(user, userId, authorities);
	}
}
