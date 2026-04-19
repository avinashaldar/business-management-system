package com.aa.main.service;

import java.util.List;

import com.aa.main.dto.AdminRequest;
import com.aa.main.entity.Admin;

public interface AdminService {
    Admin createAdmin(AdminRequest request);
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);
    Admin updateAdmin(Integer id, AdminRequest request);
    void deleteAdmin(Integer id);
    Admin login(String email, String password);
}
