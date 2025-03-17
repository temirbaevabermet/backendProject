package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByName(String name);
}
