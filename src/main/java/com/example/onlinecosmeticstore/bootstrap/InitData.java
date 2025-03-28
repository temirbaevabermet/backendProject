package com.example.OnlineCosmeticStore.bootstrap;

import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Entity.Order;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Repository.OrderRepository;
import com.example.OnlineCosmeticStore.Repository.ProductRepository;
import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitData {

    private String myProperty;

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @PostConstruct
    public void init() {

        // Создание категорий
        Category makeup = Category.builder().name("Hair Care").description("Beauty products").build();
        Category skincare = Category.builder().name("Skincare").description("Facial care").build();

        categoryRepository.saveAll(List.of(makeup, skincare));

        // Создание поставщиков
        Supplier nars = Supplier.builder().name("NARS").contactInfo("contact@nars.com").build();
        Supplier laroche = Supplier.builder().name("La Roche-Posay").contactInfo("contact@laroche.com").build();

        supplierRepository.saveAll(List.of(nars, laroche));

        // Создание продуктов
        Product foundation = Product.builder()
                .name("Foundation")
                .description("Liquid foundation")
                .price(new BigDecimal("25.99"))
                .category(makeup)
                .supplier(nars)
                .build();

        Product moisturizer = Product.builder()
                .name("Moisturizer")
                .description("Hydrating cream")
                .price(new BigDecimal("18.50"))
                .category(skincare)
                .supplier(laroche)
                .build();

        productRepository.saveAll(List.of(foundation, moisturizer));

        // Создание заказов
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .status("Pending")
                .products(Set.of(foundation, moisturizer))
                .build();

        orderRepository.save(order1);

    }

    @PreDestroy
    public void destroy() {
        log.warn("Destroying data...");
    }

    @Value("${my.value:default_value}")
    public InitData setMyProperty(String myProperty) {
        log.warn("Setting myProperty={}", myProperty);
        this.myProperty = myProperty;
        return this;
    }
}
