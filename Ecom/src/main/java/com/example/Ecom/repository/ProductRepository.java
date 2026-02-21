package com.example.Ecom.repository;

import com.example.Ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByName(String name);

    List<Product> findByCategory(String category);

    List< Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("select  p from Product p where p.name = ?1 or p.category.name = ?2 or (p.price between ?3 and ?4)")
    List<Product> findByNameOrCategoryOrPriceBetween(String name,String category,BigDecimal minPrice,BigDecimal maxPrice);
}
