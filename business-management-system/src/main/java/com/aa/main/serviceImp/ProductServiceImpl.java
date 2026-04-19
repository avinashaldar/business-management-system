package com.aa.main.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aa.main.dto.ProductRequest;
import com.aa.main.entity.Company;
import com.aa.main.entity.Product;
import com.aa.main.exception.ResourceNotFoundException;
import com.aa.main.repository.CompanyRepository;
import com.aa.main.repository.ProductRepository;
import com.aa.main.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Product createProduct(ProductRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));

        Product product = new Product();
        mapRequestToProduct(request, product, company);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCompany(Integer companyId) {
        return productRepository.findByCompanyId(companyId);
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product updateProduct(Integer id, ProductRequest request) {
        Product product = getProductById(id);
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));
        mapRequestToProduct(request, product, company);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private void mapRequestToProduct(ProductRequest request, Product product, Company company) {
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCompany(company);
    }
}
