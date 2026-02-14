package com.example.Ecom.security;

import com.example.Ecom.model.User;
import com.example.Ecom.model.type.AuthProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getJwtToken(User user) {
        return Jwts.builder().setSubject(user.getUsername()).claim("UserId",user.getId().toString()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000*60*10*1000)).signWith(getSecretKey()).compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claim =  Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
        return claim.getSubject();
    }

    public AuthProvider getProviderTypeFromRegistrationId(String registrationId) {
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProvider.GOOGLE;
            case "github" -> AuthProvider.GITHUB;
            default -> throw  new IllegalArgumentException("Unsupported OAuth2 provider" + registrationId);
        };
    }

    public String determineProviderIdFromOAuth2user(OAuth2User oAuth2User, String registrationId) {
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> throw new IllegalArgumentException("Unsuported OAuth2 provider");
        };

        if(providerId == null || providerId.isBlank()){
            throw  new IllegalArgumentException("Unable to determine providerId for OAuth2 login");
        }
        return providerId;
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
    String email = oAuth2User.getAttribute("email");
    if(email !=null && !email.isBlank()){
        return email;
    }
    return switch (registrationId.toLowerCase()){
        case "google" -> oAuth2User.getAttribute("sub");
        case "github" -> oAuth2User.getAttribute("login");
        default -> providerId;
    };

    }
}

