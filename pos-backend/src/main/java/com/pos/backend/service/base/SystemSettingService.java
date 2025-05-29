/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.base;

import com.pos.backend.dto.system_setting.SystemSettingRequest;
import com.pos.backend.dto.system_setting.SystemSettingResponse;

/**
 * @author 04dkh
 */


public interface SystemSettingService {
    SystemSettingResponse getSetting();

    SystemSettingResponse updateSetting(SystemSettingRequest request);
}
