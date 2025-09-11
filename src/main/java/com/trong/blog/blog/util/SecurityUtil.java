package com.trong.blog.blog.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.util.Base64;
import com.trong.blog.blog.domain.dto.attach.auth.UserInsideToken_;
import com.trong.blog.blog.domain.dto.response.auth.ResLoginDTO;

@Service
public class SecurityUtil {

    // Algorithm
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    public final JwtEncoder jwtEncoder;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    // lấy tham số môi trường
    @Value("${blog.jwt.base64-secret}")
    private String jwtKey;

    @Value("${blog.jwt.access-token-validity-in-seconds}")
    private long accessTokenExpiration;

    @Value("${blog.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public String createAccessToken(String email, ResLoginDTO dto) {
        UserInsideToken_ userToken = new UserInsideToken_(
                dto.getUser().getId(),
                dto.getUser().getName(),
                dto.getUser().getEmail());

        Instant now = Instant.now();// thời gian tạo
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);// thời gian hết hạn now + 1 ngày

        // @formatter:off này là Payload
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(email)//định danh người dùng là ai ở đây thì dùng với email
            .claim("user", userToken)//chỉ những thành pần biểu diễn cái object
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build(); // header
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue(); //return String
    }

    public String createRefreshToken(String email, ResLoginDTO dto) {
                UserInsideToken_ userToken = new UserInsideToken_(
                dto.getUser().getId(),
                dto.getUser().getName(),
                dto.getUser().getEmail());
        Instant now = Instant.now();// thời gian tạo
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);// thời gian hết hạn now + 1 ngày

        // @formatter:off này là Payload
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(email)
            .claim("user", userToken)
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build(); // header
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue(); //return String
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtil.JWT_ALGORITHM.getName());
    }

    public Jwt checkValidRefreshToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();
        try {
            return jwtDecoder.decode(token);
        } catch (Exception e) {
            System.out.println("refresh error: " + e.getMessage());
            throw e;
        }
    }

    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }
}
