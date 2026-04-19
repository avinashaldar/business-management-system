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
import org.springframework.web.bind.annotation.RestController;

import com.aa.main.dto.AdminRequest;
import com.aa.main.entity.Admin;
import com.aa.main.response.ApiResponse;
import com.aa.main.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Admin>> createAdmin(@Valid @RequestBody AdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Admin created successfully", adminService.createAdmin(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Admin>> login(@RequestBody AdminRequest request) {

        Admin admin = adminService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Login successful", admin)
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Admin>>> getAllAdmins() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Admins fetched successfully", adminService.getAllAdmins()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Admin>> getAdminById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin fetched successfully", adminService.getAdminById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Admin>> updateAdmin(@PathVariable Integer id, @Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin updated successfully", adminService.updateAdmin(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin deleted successfully", "Deleted id: " + id));
    }
}
