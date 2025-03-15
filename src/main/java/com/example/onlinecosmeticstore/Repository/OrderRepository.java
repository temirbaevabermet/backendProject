package com.example.onlinecosmeticstore.Repository;

import com.example.onlinecosmeticstore.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);
}
