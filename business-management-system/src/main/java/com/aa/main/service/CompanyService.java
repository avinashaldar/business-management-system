package com.aa.main.service;

import java.util.List;

import com.aa.main.dto.CompanyRequest;
import com.aa.main.entity.Company;

public interface CompanyService {
    Company createCompany(CompanyRequest request);
    List<Company> getAllCompanies();
    Company getCompanyById(Integer id);
    Company updateCompany(Integer id, CompanyRequest request);
    void deleteCompany(Integer id);
}
