package com.pos.backend.service.base;

import java.util.List;

import com.pos.backend.dto.permission.PermissionRequest;
import com.pos.backend.dto.permission.PermissionResponse;

public interface PermissionService {
    List<PermissionResponse> getAllPermissions();
    PermissionResponse getPermissionById(Long id);
    PermissionResponse createPermission(PermissionRequest request);
    PermissionResponse updatePermission(Long id, PermissionRequest request);
    void deletePermission(Long id);
}
