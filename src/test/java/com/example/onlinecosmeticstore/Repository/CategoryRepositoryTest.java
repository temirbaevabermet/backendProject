package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = Category.builder()
                .name("Cosmetics")
                .description("Cosmetic Products")
                .build();
        categoryRepository.save(category);
    }

    @Test
    public void testFindByName() {
        Optional<Category> foundCategory = categoryRepository.findByName("Cosmetics");
        assertTrue(foundCategory.isPresent());
        assertEquals("Cosmetics", foundCategory.get().getName());
    }

    @Test
    public void testFindByNameNotFound() {
        Optional<Category> foundCategory = categoryRepository.findByName("Non Existent Category");
        assertFalse(foundCategory.isPresent());
    }
}
