package com.pos.backend.config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author caodangkhoa
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Lớp này ánh xạ các thuộc tính có tiền tố 'application' trong file .properties
 * Ví dụ: application.name=My POS
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String name = "POS Backend Application"; // Giá trị mặc định

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Bạn có thể thêm các thuộc tính cấu hình khác ở đây
    // Ví dụ: private String version;
}
