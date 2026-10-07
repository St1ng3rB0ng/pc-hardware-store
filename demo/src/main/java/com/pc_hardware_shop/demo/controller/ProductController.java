package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.ProductDTO;
import com.pc_hardware_shop.demo.entity.Product;
import com.pc_hardware_shop.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isActive) {

        if (sku != null && !sku.isBlank()) {
            return ResponseEntity.ok(productService.getProductBySku(sku.trim()));
        }
        if (categoryId != null) {
            return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(productService.getProductsByName(name.trim()));
        }
        if (isActive != null) {
            return ResponseEntity.ok(productService.getProductsByActiveStatus(isActive));
        }

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product savedProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-sku")
    public ResponseEntity<Void> deleteProductBySku(@RequestParam String sku) {
        productService.deleteProductBySku(sku.trim());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-name")
    public ResponseEntity<Void> deleteProductByName(@RequestParam String name) {
        productService.deleteProductByName(name.trim());
        return ResponseEntity.noContent().build();
    }
}