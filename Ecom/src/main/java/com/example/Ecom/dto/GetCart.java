package com.example.Ecom.dto;

import com.example.Ecom.model.Product;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCart {
    private Long quantity;
    private Product product;
}
