package com.pos.backend.dto.system_setting;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SystemSettingRequest {
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
