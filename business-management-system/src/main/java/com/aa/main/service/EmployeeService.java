package com.aa.main.service;

import java.util.List;

import com.aa.main.dto.EmployeeRequest;
import com.aa.main.entity.Employee;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequest request);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByCompany(Integer companyId);
    Employee getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, EmployeeRequest request);
    void deleteEmployee(Integer id);
    Employee login(String email, String phone);
}
