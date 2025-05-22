/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.config;

/**
 *
 * @author 04dkh
 */
import com.pos.backend.model.Role;
import com.pos.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            // Check if "EMPLOYEE" role exists, if not, create it
            if (roleRepository.findByName("EMPLOYEE").isEmpty()) {
                Role employeeRole = new Role();
                employeeRole.setName("EMPLOYEE");
                // You might need to set other BaseEntity fields if they are not auto-generated
                // For example: employeeRole.setCreatedAt(java.time.LocalDateTime.now());
                // If BaseEntity handles this, you don't need to explicitly set them here.
                roleRepository.save(employeeRole);
                System.out.println("Created default role: EMPLOYEE");
            }

            // You can add other roles here as well, e.g., "ADMIN", "MANAGER"
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);
                System.out.println("Created default role: ADMIN");
            }
        };
    }
}
