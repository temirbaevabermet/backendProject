package com.example.OnlineCosmeticStore.Service;

import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import com.example.OnlineCosmeticStore.mapper.CategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        return CategoryMapper.toDto(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        categoryRepository.deleteById(id);
    }
}