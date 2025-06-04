package com.pos.backend.service.Impl;

import com.pos.backend.dto.system_setting.SystemSettingRequest;
import com.pos.backend.dto.system_setting.SystemSettingResponse;
import com.pos.backend.model.SystemSetting;
import com.pos.backend.repository.SystemSettingRepository;
import com.pos.backend.service.base.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingRepository settingRepository;

    @Override
    @Transactional(readOnly = true)
    public SystemSettingResponse getSetting() {
        SystemSetting setting = settingRepository.findById(1L).orElseThrow();
        return mapToResponse(setting);
    }

    @Override
    @Transactional
    public SystemSettingResponse updateSetting(SystemSettingRequest request) {
        SystemSetting setting = settingRepository.findById(1L).orElseThrow();
        setting.setStoreName(request.getStoreName());
        setting.setAddress(request.getAddress());
        setting.setPhone(request.getPhone());
        setting.setEmail(request.getEmail());
        setting.setLogoPath(request.getLogoPath());
        setting.setTaxRate(request.getTaxRate());
        setting.setCurrency(request.getCurrency());
        setting.setInvoicePrefix(request.getInvoicePrefix());
        setting.setPrinterName(request.getPrinterName());
        setting.setDefaultLanguage(request.getDefaultLanguage());
        setting.setBackupPath(request.getBackupPath());
        return mapToResponse(setting);
    }

    private SystemSettingResponse mapToResponse(SystemSetting s) {
        return SystemSettingResponse.builder()
                .storeName(s.getStoreName())
                .address(s.getAddress())
                .phone(s.getPhone())
                .email(s.getEmail())
                .logoPath(s.getLogoPath())
                .taxRate(s.getTaxRate())
                .currency(s.getCurrency())
                .invoicePrefix(s.getInvoicePrefix())
                .printerName(s.getPrinterName())
                .defaultLanguage(s.getDefaultLanguage())
                .backupPath(s.getBackupPath())
                .build();
    }
}
