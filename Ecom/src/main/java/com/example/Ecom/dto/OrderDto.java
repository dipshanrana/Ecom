package com.example.Ecom.dto;

import com.example.Ecom.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String address;
    private Long productId;

    private List<OrderItem> orderItems;
}
