package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.ProductDTO;
import com.pc_hardware_shop.demo.entity.Product;
import com.pc_hardware_shop.demo.repository.CategoryRepository;
import com.pc_hardware_shop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " does not exist"));
    }

    @Transactional(readOnly = true)
    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku).orElseThrow(() -> new IllegalArgumentException("Product with SKU '" + sku + "' does not exist"));
    }

    @Transactional(readOnly = true)
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Product with name '" + name + "' does not exist"));
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByActiveStatus(boolean isActive) {
        return productRepository.findByIsActive(isActive);
    }

    public Product createProduct(ProductDTO productDTO) {
        if (!categoryRepository.existsById(productDTO.categoryId())) {
            throw new IllegalArgumentException("Category by id '" + productDTO.categoryId() + " do not exists");
        }
        if (productRepository.existsBySku(productDTO.sku())) {
            throw new IllegalArgumentException("Product with sku '" + productDTO.sku() + "' already exists");
        }

        Product product = Product.builder()
                .sku(productDTO.sku().trim())
                .name(productDTO.name().trim())
                .isActive(productDTO.isActive())
                .categoryId(productDTO.categoryId())
                .stockQuantity(productDTO.stockQuantity())
                .price(productDTO.price())
                .build();

        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + "does not exist");
        }
        productRepository.deleteById(id);
    }

    public void deleteProductByName(String name) {
        if (!productRepository.existsByName(name)) {
            throw new IllegalArgumentException("Product with name '" + name + "' does not exist");
        }
        productRepository.deleteByName(name);
    }

    public void deleteProductBySku(String sku) {
        if (!productRepository.existsBySku(sku)) {
            throw new IllegalArgumentException("Product with sku '" + sku + "' does not exist");
        }
        productRepository.deleteBySku(sku);
    }
}