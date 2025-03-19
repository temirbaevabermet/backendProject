package com.example.OnlineCosmeticStore.ExceptionsTests;

import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Service.CategoryService;
import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class CategoryServiceExceptionsTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCategoryNotFoundById() {
        Long categoryId = 1L;


        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());


        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.getCategoryById(categoryId);
        });

        assertEquals("Category not found with ID: " + categoryId, thrown.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCreatingCategoryWithEmptyName() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(""); // Setting empty name


        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(categoryDTO);
        });

        assertEquals("Category name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingCategoryThatDoesNotExist() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });

        assertEquals("Category not found with ID: " + categoryId, thrown.getMessage());
    }

}