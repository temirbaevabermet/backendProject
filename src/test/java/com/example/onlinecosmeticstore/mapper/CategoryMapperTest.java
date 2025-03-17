package com.example.OnlineCosmeticStore.mapper;

import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import com.example.OnlineCosmeticStore.Entity.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void testToDto() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Cosmetics");
        category.setDescription("Beauty and skincare products");


        CategoryDTO dto = CategoryMapper.toDto(category);


        assertNotNull(dto);
        assertEquals(category.getId(), dto.getId());
        assertEquals(category.getName(), dto.getName());
        assertEquals(category.getDescription(), dto.getDescription());
    }

    @Test
    void testToEntity() {
        // Arrange
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);
        dto.setName("Cosmetics");
        dto.setDescription("Beauty and skincare products");


        Category category = CategoryMapper.toEntity(dto);


        assertNotNull(category);
        assertEquals(dto.getId(), category.getId());
        assertEquals(dto.getName(), category.getName());
        assertEquals(dto.getDescription(), category.getDescription());
    }
}
