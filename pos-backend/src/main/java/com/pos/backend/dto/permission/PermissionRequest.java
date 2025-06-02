package com.pos.backend.dto.permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {
    private String name;
    private String description;
}
