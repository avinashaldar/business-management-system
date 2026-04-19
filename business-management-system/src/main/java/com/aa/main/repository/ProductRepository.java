package com.aa.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.main.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCompanyId(Integer companyId);
}
