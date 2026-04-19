package com.aa.main.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aa.main.dto.CompanyRequest;
import com.aa.main.entity.Company;
import com.aa.main.exception.DuplicateResourceException;
import com.aa.main.exception.ResourceNotFoundException;
import com.aa.main.repository.CompanyRepository;
import com.aa.main.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(CompanyRequest request) {
        if (request.getEmail() != null && companyRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Company with this email already exists");
        }
        Company company = new Company();
        mapRequestToCompany(request, company);
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }

    @Override
    public Company updateCompany(Integer id, CompanyRequest request) {
        Company company = getCompanyById(id);
        if (request.getEmail() != null) {
            companyRepository.findByEmail(request.getEmail())
                    .filter(existing -> !existing.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new DuplicateResourceException("Another company already uses this email");
                    });
        }
        mapRequestToCompany(request, company);
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Integer id) {
        Company company = getCompanyById(id);
        companyRepository.delete(company);
    }

    private void mapRequestToCompany(CompanyRequest request, Company company) {
        company.setName(request.getName());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setAddress(request.getAddress());
        company.setWebsite(request.getWebsite());
        company.setTaxNumber(request.getTaxNumber());
    }
}
