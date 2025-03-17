package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.SupplierService;
import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SupplierControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
    }

    @Test
    void testGetAllSuppliers() throws Exception {
        SupplierDTO supplier1 = new SupplierDTO(1L, "Supplier 1", "Contact Info 1");
        SupplierDTO supplier2 = new SupplierDTO(2L, "Supplier 2", "Contact Info 2");
        List<SupplierDTO> suppliers = List.of(supplier1, supplier2);

        when(supplierService.getAllSuppliers()).thenReturn(suppliers);

        mockMvc.perform(get("/api/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Supplier 1"))
                .andExpect(jsonPath("$[1].name").value("Supplier 2"));

        verify(supplierService, times(1)).getAllSuppliers();
    }

    @Test
    void testGetSupplier() throws Exception {
        SupplierDTO supplierDTO = new SupplierDTO(1L, "Supplier 1", "Contact Info 1");

        when(supplierService.getSupplierById(anyLong())).thenReturn(supplierDTO);

        mockMvc.perform(get("/api/suppliers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Supplier 1"))
                .andExpect(jsonPath("$.contactInfo").value("Contact Info 1"));

        verify(supplierService, times(1)).getSupplierById(1L);
    }

    @Test
    void testCreateSupplier() throws Exception {
        SupplierDTO supplierDTO = new SupplierDTO(1L, "Supplier 1", "Contact Info 1");

        when(supplierService.createSupplier(any(SupplierDTO.class))).thenReturn(supplierDTO);

        mockMvc.perform(post("/api/suppliers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(supplierDTO)))
                .andExpect(status().isCreated())  // Ожидаем статус 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Supplier 1"))
                .andExpect(jsonPath("$.contactInfo").value("Contact Info 1"));

        verify(supplierService, times(1)).createSupplier(any(SupplierDTO.class));
    }

    @Test
    void testDeleteSupplier() throws Exception {
        doNothing().when(supplierService).deleteSupplier(anyLong());

        mockMvc.perform(delete("/api/suppliers/{id}", 1L))
                .andExpect(status().isNoContent());  // Ожидаем статус 204

        verify(supplierService, times(1)).deleteSupplier(1L);
    }
}