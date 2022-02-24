package com.turkeyjava.security.productservice.repository;

import com.turkeyjava.security.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
