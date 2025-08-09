/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.util;

/**
 * @author caodangkhoa
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service này chịu trách nhiệm khởi tạo dữ liệu ban đầu cho ứng dụng (seeding).
 * Nó được gọi bởi CommandLineRunner trong lớp Application chính.
 */
@Service
public class DatabaseSeedingService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeedingService.class);

    // Sau này bạn sẽ inject các repository vào đây, ví dụ:
    // private final UserRepository userRepository;
    // public DatabaseSeedingService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    public void seedUsers() {
        log.info("Khởi tạo dữ liệu cho Users...");
        // Viết logic thêm user mẫu vào CSDL ở đây
        // Ví dụ: userRepository.save(new User("admin", "password"));
    }

    public void seedProducts() {
        log.info("Khởi tạo dữ liệu cho Products...");
        // Viết logic thêm sản phẩm mẫu vào CSDL ở đây
    }
}
