package com.pos.backend;

import com.pos.backend.config.ApplicationProperties;
import com.pos.backend.util.DatabaseSeedingService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

/**
 * Lớp chính của ứng dụng Point of Sale (POS) Backend.
 *
 * @SpringBootApplication bao gồm:
 * - @Configuration: Đánh dấu lớp này là một nguồn chứa các định nghĩa bean.
 * - @EnableAutoConfiguration: Tự động cấu hình Spring Boot dựa trên các thư viện trong classpath.
 * - @ComponentScan: Quét các component, configuration, và service trong package này và các package con.
 */
@SpringBootApplication
@EnableJpaAuditing // Kích hoạt tính năng tự động điền các trường createdDate, lastModifiedDate của JPA.
@EnableCaching // Kích hoạt cơ chế caching của Spring.
@EnableAsync // Kích hoạt khả năng thực thi các phương thức bất đồng bộ với @Async.
@EnableScheduling // Kích hoạt các tác vụ được lập lịch với @Scheduled.
@EnableConfigurationProperties(ApplicationProperties.class)
// Liên kết các thuộc tính trong application.properties với một lớp Java (POJO).
public class PosBackendApplication {

    // Khởi tạo một logger để ghi log, một thực hành tốt trong mọi ứng dụng.
    private static final Logger log = LoggerFactory.getLogger(PosBackendApplication.class);

    private final Environment env;

    // Sử dụng dependency injection để tiêm Environment vào.
    public PosBackendApplication(Environment env) {
        this.env = env;
    }

    /**
     * Phương thức main, điểm khởi đầu của ứng dụng.
     */
    public static void main(String[] args) {
        // Sử dụng SpringApplicationBuilder để có nhiều tùy chỉnh hơn thay vì SpringApplication.run()
        new SpringApplicationBuilder(PosBackendApplication.class)
                .run(args);

        log.info("<<<<< ỨNG DỤNG POS BACKEND ĐÃ KHỞI ĐỘNG THÀNH CÔNG! >>>>>");
    }

    /**
     * Phương thức này được thực thi sau khi dependency injection hoàn tất.
     * Dùng để thực hiện các công việc khởi tạo.
     */
    @PostConstruct
    public void initApplication() {
        log.info("Ứng dụng đang chạy với các profile: {}", Arrays.toString(env.getActiveProfiles()));
    }

    /**
     * CommandLineRunner là một interface cho phép bạn thực thi code
     * ngay sau khi Spring Boot khởi động xong và ApplicationContext đã được tạo.
     * Rất hữu ích cho việc khởi tạo dữ liệu ban đầu (database seeding).
     *
     * @param seedingService Service chịu trách nhiệm khởi tạo dữ liệu.
     * @return một đối tượng CommandLineRunner.
     */
    @Bean
    public CommandLineRunner dataLoader(DatabaseSeedingService seedingService) {
        return args -> {
            log.info("Bắt đầu quá trình khởi tạo dữ liệu mẫu...");
            seedingService.seedUsers();
            seedingService.seedProducts();
            log.info("Hoàn tất khởi tạo dữ liệu mẫu.");
        };
    }
}