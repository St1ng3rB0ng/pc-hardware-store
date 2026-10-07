package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.ProductDTO;
import com.pc_hardware_shop.demo.entity.Product;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.CategoryRepository;
import com.pc_hardware_shop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id '" + id + "' not found"));
    }

    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Product with SKU '" + sku + "' not found"));
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByActiveStatus(boolean isActive) {
        return productRepository.findByIsActive(isActive);
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        String sku = productDTO.sku().trim();
        String name = productDTO.name().trim();

        if (!categoryRepository.existsById(productDTO.categoryId())) {
            throw new IllegalArgumentException("Category with id '" + productDTO.categoryId() + "' does not exist");
        }

        if (productRepository.existsBySku(sku)) {
            throw new IllegalArgumentException("Product with SKU '" + sku + "' already exists");
        }

        Product product = Product.builder()
                .sku(sku)
                .name(name)
                .isActive(productDTO.isActive())
                .categoryId(productDTO.categoryId())
                .stockQuantity(productDTO.stockQuantity())
                .price(productDTO.price())
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("Successfully created new product with ID: {}, SKU: '{}' and Name: '{}'",
                savedProduct.getId(), savedProduct.getSku(), savedProduct.getName());

        return savedProduct;
    }

    @Transactional
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product with id '" + id + "' not found");
        }
        productRepository.deleteById(id);
        log.info("Successfully deleted product with ID: {}", id);
    }

    @Transactional
    public void deleteProductByName(String name) {
        if (!productRepository.existsByName(name)) {
            throw new NotFoundException("Products with name '" + name + "' not found");
        }
        productRepository.deleteByName(name);
        log.info("Successfully deleted products with name: '{}'", name);
    }

    @Transactional
    public void deleteProductBySku(String sku) {
        if (!productRepository.existsBySku(sku)) {
            throw new NotFoundException("Product with SKU '" + sku + "' not found");
        }
        productRepository.deleteBySku(sku);
        log.info("Successfully deleted product with SKU: '{}'", sku);
    }
}