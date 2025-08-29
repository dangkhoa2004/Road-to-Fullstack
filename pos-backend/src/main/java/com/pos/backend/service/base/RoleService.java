/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.base;

/**
 * @author 04dkh
 */

import java.util.Optional;

import com.pos.backend.model.Role;

public interface RoleService {
    Optional<Role> findRoleById(Long id);

    void saveRole(Role role);
}
