package com.aa.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ADMIN
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/admin-login";
    }

    @GetMapping("/admin/register")
    public String adminRegister() {
        return "admin/admin-register";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/admin-dashboard";
    }

    // EMPLOYEE
    @GetMapping("/employee/login")
    public String empLogin() {
        return "employee/employee-login";
    }

    @GetMapping("/employee/register")
    public String empRegister() {
        return "employee/employee-register";
    }

    @GetMapping("/employee/dashboard")
    public String empDashboard() {
        return "employee/employee-dashboard";
    }
}
