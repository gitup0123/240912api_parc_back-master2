package com.parc.api.model.mapper;

import com.parc.api.model.dto.RoleDto;
import com.parc.api.model.entity.Role;

public class RoleMapper {

    public static RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setIdRole(role.getId());
        roleDto.setLibRole(role.getLibRole());
        return roleDto;
    }

    public static Role toEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getIdRole());
        role.setLibRole(roleDto.getLibRole());
        return role;
    }
}
