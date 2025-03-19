package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.CategoryService;
import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @GetMapping
    public List<CategoryDTO> getCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get a category by ID", description = "Retrieve a single category based on the provided ID")
    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Create a new category", description = "Create a new category with the provided details")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a category by ID", description = "Delete a category by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}