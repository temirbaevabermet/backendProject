package com.example.OnlineCosmeticStore.Integration;

import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import com.example.OnlineCosmeticStore.Service.SupplierService;
import com.example.OnlineCosmeticStore.Controller.SupplierController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SupplierControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService supplierService;

    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setName("Test Supplier");
        supplier.setContactInfo("test@example.com");
        supplierRepository.save(supplier);
    }

    @Test
    void testGetAllSuppliers() {
        // Отправка запроса на получение всех поставщиков
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/api/suppliers", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0, "The list of suppliers must not be empty");
    }

    @Test
    void testGetSupplierById() {
        // Отправка запроса на получение поставщика по ID
        ResponseEntity<SupplierDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/api/suppliers/" + supplier.getId(), SupplierDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "The answer must not be empty");
        assertEquals(supplier.getId(), response.getBody().getId(), "Vendor ID does not match");
    }

    @Test
    void testCreateSupplier() {
        // Создание нового поставщика
        SupplierDTO newSupplierDTO = new SupplierDTO();
        newSupplierDTO.setName("New Supplier");
        newSupplierDTO.setContactInfo("new@example.com");

        ResponseEntity<SupplierDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/api/suppliers", newSupplierDTO, SupplierDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId(), "The new supplier ID must not be null");
        assertEquals("New Supplier", response.getBody().getName(), "The new supplier name does not match");
    }

    @Test
    void testDeleteSupplier() {
        // Удаление поставщика по ID
        Long supplierId = supplier.getId();

        restTemplate.delete("http://localhost:" + port + "/api/suppliers/" + supplierId);

        assertFalse(supplierRepository.findById(supplierId).isPresent(), "The supplier must not exist in the database");
    }
}
