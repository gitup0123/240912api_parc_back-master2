package com.parc.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {
    private Integer id;
    private String pseudo;
    private String email;
    private String mdp;
    private LocalDate dateCreation;
    private Boolean isActive = false;
    private RoleDto idRole;

}
