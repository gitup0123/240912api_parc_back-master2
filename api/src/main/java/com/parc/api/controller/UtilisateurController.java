package com.parc.api.controller;

import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "utilisateur", description = "Opérations sur les utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;


    @GetMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(summary = "Affiche la liste des utilisateurs",
            description = "Retourne une liste d'utilisateur",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateur() {
        return this.utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur') or @securityService.isCurrentUserOrAdmin(#id, authentication)")
    @Operation(summary = "Obtenir un utilisateur par ID",
            description = "Retourne un utilisateur spécifique basé sur son ID",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Integer id) {
        return this.utilisateurService.getUtilisateurById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur') or (@securityService.isCurrentUserOrAdmin(#id, authentication) and hasAuthority('Utilisateur'))")
    @Operation(summary = "Mettre à jour un utilisateur",
            description = "Met à jour les informations d'un utilisateur basé sur son ID",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "400", description = "Données invalides"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Integer id, @RequestBody @Valid UtilisateurDto utilisateurDto) {
        return this.utilisateurService.updateUtilisateur(id, utilisateurDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("('Administrateur')")
    @Operation(summary = "Supprimer un utilisateur",
            description = "Supprime un utilisateur basé sur son ID",
            operationId = "utilisateurs",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        return this.utilisateurService.deleteUtilisateur(id);
    }
}

