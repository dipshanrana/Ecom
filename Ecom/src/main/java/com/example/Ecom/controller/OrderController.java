package com.example.Ecom.controller;


import com.example.Ecom.dto.OrderDto;
import com.example.Ecom.model.User;
import com.example.Ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeorder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(orderService.saveOrder(orderDto,user));
    }

}
