package com.example.Ecom.security;

import com.example.Ecom.dto.LoginRequest;
import com.example.Ecom.dto.LoginResponse;
import com.example.Ecom.dto.SignUpRequest;
import com.example.Ecom.dto.SignUpResponse;
import com.example.Ecom.model.Customer;
import com.example.Ecom.model.User;
import com.example.Ecom.model.type.AuthProvider;
import com.example.Ecom.model.type.RoleType;
import com.example.Ecom.repository.CustomerRepository;
import com.example.Ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        User user  = (User) authentication.getPrincipal();
        String token = authUtil.getJwtToken(user);
        return new LoginResponse(token,user.getId());

    }


    public  User signUpInternal(SignUpRequest signUpRequest, AuthProvider authProvider, String providerId) {
        User user = userRepository.findByUsername(signUpRequest.getUsername()).orElse(null);
        if(user != null ) throw new IllegalArgumentException("User already exist");
        user = User.builder().authProvider(authProvider).providerId(providerId).username(signUpRequest.getUsername()).roles(Set.of(RoleType.CUSTOMER)).build();
        if(authProvider == authProvider.EMAIL){
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        }


        user =  userRepository.save(user);
        Customer customer = Customer.builder().name(signUpRequest.getUsername()).email(signUpRequest.getEmail()).user(user).build();
        customerRepository.save(customer);

        return user;
    }


    public SignUpResponse signup(SignUpRequest signUpRequest) {
        User user = signUpInternal(signUpRequest,AuthProvider.EMAIL,null);
        return new SignUpResponse(user.getId(),user.getUsername());
    }


    @Transactional
    public ResponseEntity<LoginResponse> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProvider authProvider =authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2user(oAuth2User,registrationId);
        User user = userRepository.findByProviderIdAndAuthProvider(providerId,authProvider).orElse(null);
        String email = oAuth2User.getAttribute("email");
        User emailUser = userRepository.findByUsername(email).orElse(null);
        String name = oAuth2User.getAttribute("name");
        if(user == null && emailUser == null){
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
            user = signUpInternal(new SignUpRequest(username,null,email),authProvider,providerId);
        } else if (user !=null) {
            if (email !=null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }
       else {
           throw new BadCredentialsException("This email is already registered with provider"+email);
        }
       LoginResponse loginResponse = new LoginResponse(authUtil.getJwtToken(user),user.getId());
       return ResponseEntity.ok(loginResponse);
    }


}
