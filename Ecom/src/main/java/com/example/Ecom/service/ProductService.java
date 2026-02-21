package com.example.Ecom.service;


import com.example.Ecom.dto.ProductDto;
import com.example.Ecom.dto.ProductInfo;
import com.example.Ecom.dto.ProductResponseDto;
import com.example.Ecom.model.Category;
import com.example.Ecom.model.Product;
import com.example.Ecom.repository.CategoryRepository;
import com.example.Ecom.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public ProductResponseDto saveProduct(ProductDto productDto, MultipartFile image) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImage(image.getBytes());
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public ProductInfo getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        String base64Image = Base64.getEncoder().encodeToString(product.getImage());

        ProductInfo productInfo=  (modelMapper.map(product,ProductInfo.class));
        productInfo.setImage(base64Image);
        return productInfo;
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductDto productDto, MultipartFile image) throws IOException {
        Category category = categoryRepository.findById(productDto.getCategory_id()).orElseThrow();
        Product product = productRepository.findById(id).orElseThrow();
        product.setImage(image.getBytes());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public Void deleteProduct(Long id) {
        productRepository.deleteById(id);
        return null;
    }

    public List<ProductInfo> getAllProduct() {
       return  productRepository.findAll().stream().map(product ->
       {
           String image = Base64.getEncoder().encodeToString(product.getImage());
           ProductInfo productInfo = modelMapper.map(product,ProductInfo.class);
           productInfo.setImage(image);
            return productInfo;
       }).toList();
    }

    public List<ProductInfo> searchProduct(String name, String category, BigDecimal minPrice, BigDecimal maxPrice) {
        minPrice = minPrice != null ? minPrice : BigDecimal.ZERO;
        maxPrice = maxPrice != null ? maxPrice : new BigDecimal("999999999999999");
        List<Product> products = productRepository.findByNameOrCategoryOrPriceBetween(name, category, minPrice, maxPrice);
        return products.stream().map(product ->
                {
                    String image = Base64.getEncoder().encodeToString(product.getImage());
                    ProductInfo productInfo = modelMapper.map(product, ProductInfo.class);
                    productInfo.setImage(image);
                    return productInfo;
                }
        ).toList();
    }
}
