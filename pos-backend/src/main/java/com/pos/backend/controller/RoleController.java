/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.role.RoleRequest;
import com.pos.backend.dto.role.RoleResponse;
import com.pos.backend.model.Role;
import com.pos.backend.service.base.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> findRoleById(@PathVariable Long id) {
        Optional<Role> optionalRole = roleService.findRoleById(id);
        if (optionalRole.isPresent()) {
            RoleResponse response = new RoleResponse(optionalRole.get());
            return ResponseEntity.ok(new ApiResponse<>("Tìm thấy vai trò", "200", response));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Không tìm thấy vai trò với ID này", "404", null));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<RoleResponse>> saveRole(@Valid @RequestBody RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        roleService.saveRole(role);
        RoleResponse response = new RoleResponse(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Vai trò đã được lưu", "201", response));
    }
}

