package com.aa.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.main.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(String email);
    List<Employee> findByCompanyId(Integer companyId);
    Optional<Employee> findByEmailAndPhone(String email, String phone);
    Optional<Employee> findByEmail(String email);
}
