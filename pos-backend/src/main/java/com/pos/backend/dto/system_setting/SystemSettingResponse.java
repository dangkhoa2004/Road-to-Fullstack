package com.pos.backend.dto.system_setting;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SystemSettingResponse {
    private String storeName;
    private String address;
    private String phone;
    private String email;
    private String logoPath;
    private BigDecimal taxRate;
    private String currency;
    private String invoicePrefix;
    private String printerName;
    private String defaultLanguage;
    private String backupPath;
}
