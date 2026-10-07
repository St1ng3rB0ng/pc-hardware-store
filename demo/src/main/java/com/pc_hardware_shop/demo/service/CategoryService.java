package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.CategoryDTO;
import com.pc_hardware_shop.demo.entity.Category;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id '" + id + "' not found"));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category with name '" + name + "' not found"));
    }

    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        String trimmedName = categoryDTO.name().trim();

        if (categoryRepository.existsByName(trimmedName)) {
            throw new IllegalArgumentException("Category with name '" + trimmedName + "' already exists");
        }

        Category category = new Category();
        category.setName(trimmedName);

        Category savedCategory = categoryRepository.save(category);
        log.info("Successfully created new category with ID: {} and name: '{}'",
                savedCategory.getId(), savedCategory.getName());

        return savedCategory;
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id '" + id + "' not found");
        }
        categoryRepository.deleteById(id);
        log.info("Successfully deleted category with ID: {}", id);
    }

    @Transactional
    public void deleteCategoryByName(String name) {
        if (!categoryRepository.existsByName(name)) {
            throw new NotFoundException("Category with name '" + name + "' not found");
        }
        categoryRepository.deleteByName(name);
        log.info("Successfully deleted category with name: '{}'", name);
    }
}