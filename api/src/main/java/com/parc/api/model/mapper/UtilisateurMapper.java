package com.parc.api.model.mapper;

import com.parc.api.model.dto.RoleDto;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Role;
import com.parc.api.model.entity.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class UtilisateurMapper {
    public static UtilisateurDto toDto(Utilisateur utilisateur) {

        RoleDto roleDto = RoleMapper.toDto(utilisateur.getIdRole());

        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setId(utilisateur.getId());
        utilisateurDto.setPseudo(utilisateur.getPseudo());
        utilisateurDto.setEmail(utilisateur.getEmail());
        utilisateurDto.setMdp(utilisateur.getMdp());
        utilisateurDto.setDateCreation(utilisateur.getDateCreation());
        utilisateurDto.setIsActive(utilisateur.getIsActive());
        utilisateurDto.setIdRole(roleDto);
        return utilisateurDto;
    }
    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {

        Role role = RoleMapper.toEntity(utilisateurDto.getIdRole());

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setPseudo(utilisateurDto.getPseudo());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setMdp(utilisateurDto.getMdp());
        utilisateur.setDateCreation(utilisateurDto.getDateCreation());
        utilisateur.setIsActive(utilisateurDto.getIsActive());
        utilisateur.setIdRole(role);
        return utilisateur;
    }
}
