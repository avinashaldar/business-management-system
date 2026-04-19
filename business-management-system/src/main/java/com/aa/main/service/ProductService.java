package com.aa.main.service;

import java.util.List;

import com.aa.main.dto.ProductRequest;
import com.aa.main.entity.Product;

public interface ProductService {
    Product createProduct(ProductRequest request);
    List<Product> getAllProducts();
    List<Product> getProductsByCompany(Integer companyId);
    Product getProductById(Integer id);
    Product updateProduct(Integer id, ProductRequest request);
    void deleteProduct(Integer id);
}
