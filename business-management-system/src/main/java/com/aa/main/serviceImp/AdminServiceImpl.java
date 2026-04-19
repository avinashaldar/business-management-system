package com.aa.main.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aa.main.dto.AdminRequest;
import com.aa.main.entity.Admin;
import com.aa.main.exception.DuplicateResourceException;
import com.aa.main.exception.ResourceNotFoundException;
import com.aa.main.repository.AdminRepository;
import com.aa.main.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin login(String email, String password) {

        return adminRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
    
    @Override
    public Admin createAdmin(AdminRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Admin with this email already exists");
        }
        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setPassword(request.getPassword());
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
    }

    @Override
    public Admin updateAdmin(Integer id, AdminRequest request) {
        Admin admin = getAdminById(id);
        adminRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Another admin already uses this email");
                });

        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setPassword(request.getPassword());
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Integer id) {
        Admin admin = getAdminById(id);
        adminRepository.delete(admin);
    }
}
