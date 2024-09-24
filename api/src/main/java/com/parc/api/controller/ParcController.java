package com.parc.api.controller;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.service.ParcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/parcs")
@Tag(name = "parc", description = "Opérations liées aux parcs")
public class ParcController {

    private final ParcService parcService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des parcs",
            description = "Retourne une liste de tous les parcs.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des parcs récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ParcDto>> getAllParc() {
        return this.parcService.getAllParc();
    }

    @GetMapping("/{nomParc}")
    @Operation(
            summary = "Affiche un parc par nom",
            description = "Retourne les détails d'un parc basé sur son nom.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parc trouvé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            }
    )
    public ResponseEntity<ParcDto> getNomParc(
            @Parameter(description = "Nom du parc à rechercher") @PathVariable String nomParc) {
        return this.parcService.getNomParc(nomParc);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée un nouveau parc",
            description = "Ajoute un nouveau parc à la base de données.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Parc créé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ParcDto> createParc(
            @Parameter(description = "Détails du parc à créer") @RequestBody ParcDto parcDto) {
        return this.parcService.createParc(parcDto);
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour un parc",
            description = "Met à jour les informations d'un parc existant.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parc mis à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ParcDto> updateParc(
            @Parameter(description = "ID du parc à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations du parc") @RequestBody ParcDto parcDto) {
        return this.parcService.updateParc(id, parcDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un parc",
            description = "Supprime un parc basé sur son ID.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Parc supprimé avec succès"
                    ),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            }
    )
    public ResponseEntity<Void> deleteParc(
            @Parameter(description = "ID du parc à supprimer") @PathVariable int id) {
        return this.parcService.deleteParc(id);
    }
}
