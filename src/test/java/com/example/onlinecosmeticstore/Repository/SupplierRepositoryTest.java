package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.OnlineCosmeticStoreApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OnlineCosmeticStoreApplication.class)
@ExtendWith(SpringExtension.class)
public class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        supplier = Supplier.builder()
                .name("Test Supplier")
                .contactInfo("test@contact.com")
                .build();
        supplierRepository.save(supplier);
    }

    @Test
    public void testFindByName() {
        Optional<Supplier> foundSupplier = supplierRepository.findByName("Test Supplier");
        assertTrue(foundSupplier.isPresent());
        assertEquals("Test Supplier", foundSupplier.get().getName());
    }

    @Test
    public void testFindByNameNotFound() {
        Optional<Supplier> foundSupplier = supplierRepository.findByName("Non Existent Supplier");
        assertFalse(foundSupplier.isPresent());
    }
}