package com.example.Ecom.repository;

import com.example.Ecom.model.User;
import com.example.Ecom.model.type.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndAuthProvider(String providerId, AuthProvider authProvider);
}
