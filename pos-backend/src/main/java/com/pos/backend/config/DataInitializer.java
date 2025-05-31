package com.pos.backend.config;

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
            if (roleRepository.findByName("Nhân viên").isEmpty()) {
                Role employeeRole = new Role();
                employeeRole.setName("Nhân viên");
                roleRepository.save(employeeRole);
            }
            if (roleRepository.findByName("Quản trị viên").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName("Quản trị viên");
                roleRepository.save(adminRole);
            }
        };
    }
}
