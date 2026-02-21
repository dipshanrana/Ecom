package com.example.Ecom.repository;

import com.example.Ecom.dto.GetCart;
import com.example.Ecom.model.Cart;
import com.example.Ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<GetCart>findByUser(User user);
}