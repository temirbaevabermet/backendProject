package com.example.OnlineCosmeticStore.Integration;

import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Service.CategoryService;
import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Controller.CategoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CategoryControllerIntegrationTest {

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Skincare");
        categoryDTO.setDescription("Skincare products");

        mockMvc.perform(post("/api/categories")
                        .contentType("application/json")
                        .content("{\"name\":\"Skincare\", \"description\":\"Skincare products\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Skincare"))
                .andExpect(jsonPath("$.description").value("Skincare products"));
    }

    @Test
    public void testGetCategories() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hair Care"));
    }

    @Test
    public void testGetCategoryById() throws Exception {

        Category category = new Category();
        category.setName("Makeup");
        category.setDescription("Makeup products");
        Category savedCategory = categoryRepository.save(category);

        mockMvc.perform(get("/api/categories/{id}", savedCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Makeup"))
                .andExpect(jsonPath("$.description").value("Makeup products"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Category category = new Category();
        category.setName("Fragrance");
        category.setDescription("Perfumes and fragrances");
        Category savedCategory = categoryRepository.save(category);

        mockMvc.perform(delete("/api/categories/{id}", savedCategory.getId()))
                .andExpect(status().isNoContent());

        boolean exists = categoryRepository.existsById(savedCategory.getId());
        assert !exists;
    }
}