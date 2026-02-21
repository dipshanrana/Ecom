package com.example.Ecom.service;


import com.example.Ecom.dto.CartDto;
import com.example.Ecom.dto.GetCart;
import com.example.Ecom.dto.UpdateCart;
import com.example.Ecom.model.Cart;
import com.example.Ecom.model.Product;
import com.example.Ecom.model.User;
import com.example.Ecom.repository.CartRepository;
import com.example.Ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public void addToCart(CartDto cartDto, User user) {
       Product product =  productRepository.findById(cartDto.getProductId()).orElseThrow();
       Cart cart = Cart.builder().product(product).quantity(cartDto.getQuantity()).user(user).build();

    }

    public void updateCart(UpdateCart updateCart) {
        Cart cart  = cartRepository.findById(updateCart.getId()).orElseThrow();
        cart.setQuantity(updateCart.getQuantity());
        cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public List<GetCart> getAllCart(User user) {
       return cartRepository.findByUser(user);
    }
}
