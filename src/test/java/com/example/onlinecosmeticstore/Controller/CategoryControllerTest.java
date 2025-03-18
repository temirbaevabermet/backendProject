package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.CategoryService;
import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetCategories() throws Exception {
        // Используем builder для создания объектов CategoryDTO
        CategoryDTO category1 = CategoryDTO.builder()
                .id(1L)
                .name("Category 1")
                .build();
        CategoryDTO category2 = CategoryDTO.builder()
                .id(2L)
                .name("Category 2")
                .build();

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category1, category2));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        // Используем builder для создания объекта CategoryDTO
        CategoryDTO category = CategoryDTO.builder()
                .id(1L)
                .name("Category 1")
                .build();
        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/api/categories/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"));

        verify(categoryService, times(1)).getCategoryById(anyLong());
    }

    @Test
    public void testCreateCategory() throws Exception {
        // Создаем объект CategoryDTO
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("New Category")
                .build();
        // Создаем объект CategoryDTO, который будет возвращен после создания
        CategoryDTO createdCategory = CategoryDTO.builder()
                .id(1L)
                .name("New Category")
                .build();

        // Мокаем поведение сервиса
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(createdCategory);

        // Выполняем запрос и проверяем статус
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())  // Ожидаем статус 201 Created
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Category"));

        // Проверяем, что метод createCategory был вызван один раз
        verify(categoryService, times(1)).createCategory(any(CategoryDTO.class));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyLong());

        mockMvc.perform(delete("/api/categories/{id}", 1L))
                .andExpect(status().isNoContent())  // Ожидаем статус 204 No Content
                .andExpect(content().string(""));

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }
}