package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;  // Добавляем репозиторий для Supplier

    private Category category;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        // Создание и сохранение категории
        category = Category.builder()
                .name("Skincare")
                .description("Skin care products")
                .build();
        category = categoryRepository.save(category);

        // Создание и сохранение поставщика
        supplier = Supplier.builder()
                .name("Beauty Corp")
                .contactInfo("123-456-7890")
                .build();
        supplier = supplierRepository.save(supplier);

        // Создание продуктов с установкой связи с категорией и поставщиком
        Product product1 = Product.builder()
                .name("Moisturizer")
                .description("Hydrating moisturizer")
                .price(BigDecimal.valueOf(20.99))
                .category(category)
                .supplier(supplier)  // Привязываем поставщика
                .build();

        Product product2 = Product.builder()
                .name("Sunscreen")
                .description("SPF 50 sunscreen")
                .price(BigDecimal.valueOf(15.49))
                .category(category)
                .supplier(supplier)  // Привязываем поставщика
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    void testFindByCategoryName() {
        List<Product> products = productRepository.findByCategoryName("Skincare");
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(2, products.size());
    }
}