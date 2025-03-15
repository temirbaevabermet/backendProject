package com.example.onlinecosmeticstore.Repository;

import com.example.onlinecosmeticstore.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
