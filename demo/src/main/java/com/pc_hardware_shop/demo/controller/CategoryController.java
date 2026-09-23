package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.CategoryDTO;
import com.pc_hardware_shop.demo.entity.Category;
import com.pc_hardware_shop.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO category) {
        Category savedCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    // .../by-id/value
    @DeleteMapping("/delete/by-id/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build(); // sends 204 No Content
    }

    // .../by-name?name=value
    @DeleteMapping("/delete/by-name")
    public ResponseEntity<Void> deleteCategoryByName(@RequestParam String name) {
        categoryService.deleteCategoryByName(name);
        return ResponseEntity.noContent().build(); // sends 204 No Content
    }

}
