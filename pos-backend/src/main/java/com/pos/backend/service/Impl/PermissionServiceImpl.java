package com.pos.backend.service.Impl;

import com.pos.backend.dto.permission.PermissionRequest;
import com.pos.backend.dto.permission.PermissionResponse;
import com.pos.backend.model.Permission;
import com.pos.backend.repository.PermissionRepository;
import com.pos.backend.service.base.PermissionService;
import com.pos.backend.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return convertToDto(permission);
    }

    @Override
    @Transactional
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = new Permission();
        mapRequestToEntity(request, permission);
        Permission savedPermission = permissionRepository.save(permission);
        return convertToDto(savedPermission);
    }

    @Override
    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));

        mapRequestToEntity(request, existingPermission);
        Permission updatedPermission = permissionRepository.save(existingPermission);
        return convertToDto(updatedPermission);
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

    // Helper: map từ DTO sang entity
    private void mapRequestToEntity(PermissionRequest request, Permission permission) {
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
    }

    // Helper: map từ entity sang DTO
    private PermissionResponse convertToDto(Permission permission) {
        PermissionResponse dto = new PermissionResponse();
        dto.setId(permission.getId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        return dto;
    }
}
