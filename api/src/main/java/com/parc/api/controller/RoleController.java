package com.parc.api.controller;

import com.parc.api.model.dto.RoleDto;
import com.parc.api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Tag(name = "role", description = "Opérations sur les rôles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("('Administrateur')")
    @Operation(
            summary = "Affiche la liste des rôles",
            description = "Retourne une liste de tous les rôles.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des rôles retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<RoleDto>> getAllRole() {
        return this.roleService.getAllRole();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Affiche un rôle par ID",
            description = "Retourne un rôle basé sur son ID.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
            }
    )
    public ResponseEntity<RoleDto> getRoleById(@PathVariable int id) {
        return this.roleService.getRoleById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée un nouveau rôle",
            description = "Ajoute un nouveau rôle à la base de données.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Rôle créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        return this.roleService.createRole(roleDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour un rôle",
            description = "Met à jour les informations d'un rôle existant basé sur son ID.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle mis à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Rôle non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<RoleDto> updateRole(@PathVariable int id, @RequestBody RoleDto roleDto) {
        return this.roleService.updateRole(id, roleDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un rôle",
            description = "Supprime un rôle basé sur son ID.",
            operationId = "role",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Rôle supprimé"),
                    @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
            }
    )
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        return this.roleService.deleteRole(id);
    }
}


