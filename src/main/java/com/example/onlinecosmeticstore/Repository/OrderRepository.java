package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);
}
