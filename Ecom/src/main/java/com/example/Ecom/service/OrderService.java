package com.example.Ecom.service;


import com.example.Ecom.dto.OrderDto;
import com.example.Ecom.model.Customer;
import com.example.Ecom.model.Order;
import com.example.Ecom.model.OrderItem;
import com.example.Ecom.model.User;
import com.example.Ecom.repository.CustomerRepository;
import com.example.Ecom.repository.OrderItemRepository;
import com.example.Ecom.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public String saveOrder(OrderDto orderDto, User user) {
        List<OrderItem> orderItems = orderDto.getOrderItems();
        Customer customer = customerRepository.findById(user.getId()).orElseThrow();
        Order order = Order.builder().items(orderItems).address(orderDto.getAddress()).customer(customer).build();
       orderItems.forEach(item ->item.setOrder(order));

        orderRepository.save(order);


        return "Order saved";
    }
}
