package com.example.Ecom.controller;

import com.example.Ecom.dto.ProductDto;
import com.example.Ecom.dto.ProductInfo;
import com.example.Ecom.dto.ProductResponseDto;
import com.example.Ecom.repository.ProductRepository;
import com.example.Ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/productName")
    @PreAuthorize("hasAuthority('product:manage')")
    public String product(){
        return "check";
    }

    @PostMapping("/addproduct")
    @PreAuthorize("hasAuthority('product:manage')")
    public ProductResponseDto addProduct(@RequestPart("product") ProductDto productDto, @RequestPart("image")MultipartFile image) throws IOException {
        return productService.saveProduct(productDto,image);
    }

    @PostMapping("/getproduct")
    public ProductInfo getProduct(@RequestParam Long id){
        return productService.getProduct(id);
    }

    @PutMapping("/updateproduct/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestPart("product") ProductDto productDto,@RequestPart("image") MultipartFile image) throws IOException {
        return productService.updateProduct(id,productDto,image);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.deleteProduct(id));
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<List<ProductInfo>> getAllProduct(){
        return ResponseEntity.ok().body(productService.getAllProduct());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductInfo>> searchProduct(@RequestParam("name") String name, @RequestParam("category") String category, @RequestParam("minPrice") BigDecimal minPrice, @RequestParam("maxPrice") BigDecimal maxPrice ){
        return ResponseEntity.ok().body(productService.searchProduct(name,category,minPrice,maxPrice));
    }

}
