package com.pos.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false); // Không cần body
        filter.setMaxPayloadLength(1000); // Giới hạn body nếu có
        filter.setIncludeHeaders(false); // Không cần header
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}

