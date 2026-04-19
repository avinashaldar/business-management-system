package com.aa.main.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aa.main.dto.EmployeeRequest;
import com.aa.main.entity.Company;
import com.aa.main.entity.Employee;
import com.aa.main.exception.DuplicateResourceException;
import com.aa.main.exception.ResourceNotFoundException;
import com.aa.main.repository.CompanyRepository;
import com.aa.main.repository.EmployeeRepository;
import com.aa.main.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Employee createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee with this email already exists");
        }
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));

        Employee employee = new Employee();
        mapRequestToEmployee(request, employee, company);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByCompany(Integer companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee updateEmployee(Integer id, EmployeeRequest request) {

        Employee employee = getEmployeeById(id);

        // ✅ EMAIL UPDATE (optional - with validation)
        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(employee.getEmail())) {

            employeeRepository.findByEmail(request.getEmail())
                    .filter(existing -> !existing.getId().equals(id))
                    .ifPresent(e -> {
                        throw new DuplicateResourceException("Email already in use");
                    });

            employee.setEmail(request.getEmail());
        }

        // ✅ ALLOWED UPDATES
        if (request.getFullName() != null)
            employee.setFullName(request.getFullName());

        if (request.getPhone() != null)
            employee.setPhone(request.getPhone());

        // ❌ DO NOT UPDATE THESE (ignore from request)
        // employee.setDesignation(...)
        // employee.setCompany(...)

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    private void mapRequestToEmployee(EmployeeRequest request, Employee employee, Company company) {
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
//        employee.setSalary(request.getSalary());
        employee.setCompany(company);
    }
    @Override
    public Employee login(String email, String phone) {

        return employeeRepository.findByEmailAndPhone(email, phone)
                .orElseThrow(() -> new RuntimeException("Invalid email or phone"));
    }
}
