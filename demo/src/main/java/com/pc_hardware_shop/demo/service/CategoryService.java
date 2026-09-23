package com.pc_hardware_shop.demo.service;


import com.pc_hardware_shop.demo.dto.CategoryDTO;
import com.pc_hardware_shop.demo.entity.Category;
import com.pc_hardware_shop.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Category with name '" + name + "' does not exist"));
    }
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category with id '" + id + "' does not exist"));
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.name())) {
            throw new IllegalArgumentException("Category with name '" + categoryDTO.name() + "' already exists");
        }
        Category category = new Category();
        category.setName(categoryDTO.name().trim());
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with ID '" + id + "' does not exist");
        }
        categoryRepository.deleteById(id);
    }

    public void deleteCategoryByName(String name) {
        if (!categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category with name '" + name + "' does not exist");
        }
        categoryRepository.deleteByName(name);
    }
}
