package com.example.OnlineCosmeticStore.bootstrap;

import com.example.OnlineCosmeticStore.Entity.*;
import com.example.OnlineCosmeticStore.Repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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


        if (userRepository.count() == 0) { // чтобы не создавать заново каждый раз
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("adminpass"))
                    .role(Role.EMPLOYEE)
                    .enabled(true)
                    .build();

            User customer = User.builder()
                    .username("customer")
                    .email("customer@example.com")
                    .password(passwordEncoder.encode("customerpass"))
                    .role(Role.CUSTOMER)
                    .enabled(true)
                    .build();

            User supplier = User.builder()
                    .username("supplier")
                    .email("supplier@example.com")
                    .password(passwordEncoder.encode("supplierpass"))
                    .role(Role.SUPPLIER)
                    .enabled(true)
                    .build();

            userRepository.save(admin);
            userRepository.save(customer);
            userRepository.save(supplier);
        }

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
