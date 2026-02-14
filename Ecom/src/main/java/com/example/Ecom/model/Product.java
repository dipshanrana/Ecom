package com.example.Ecom.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;
}
