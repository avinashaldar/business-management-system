package com.aa.main.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aa.main.dto.EmployeeRequest;
import com.aa.main.dto.LoginRequest;
import com.aa.main.entity.Employee;
import com.aa.main.response.ApiResponse;
import com.aa.main.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Employee created successfully", employeeService.createEmployee(request)));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Employee>> login(@RequestBody LoginRequest request) {

        Employee employee = employeeService.login(request.getEmail(), request.getPhone());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", employee)
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployees(@RequestParam(required = false) Integer companyId) {
        List<Employee> employees = companyId != null
                ? employeeService.getEmployeesByCompany(companyId)
                : employeeService.getAllEmployees();
        return ResponseEntity.ok(new ApiResponse<>(true, "Employees fetched successfully", employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Employee fetched successfully", employeeService.getEmployeeById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Employee updated successfully", employeeService.updateEmployee(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Employee deleted successfully", "Deleted id: " + id));
    }
}
