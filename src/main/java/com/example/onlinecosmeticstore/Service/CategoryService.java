package com.example.OnlineCosmeticStore.Service;

import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Repository.ProductRepository;
import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import com.example.OnlineCosmeticStore.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return CategoryMapper.toDto(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent(category -> {
            category.getProducts().forEach(product -> productRepository.delete(product));
        });

        categoryRepository.deleteById(id);
    }
}