package com.example.grainmarketplace.repository;

import com.example.grainmarketplace.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
