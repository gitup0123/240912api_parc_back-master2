package com.parc.api.controller;


import com.parc.api.model.dto.EnregistrerDto;
import com.parc.api.service.EnregistrerService;
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
@RequestMapping("/enregistrer")
@Tag(name = "enregistrer", description = "Opérations liées à l'entité Enregistrer")
public class EnregistrerController {

    private final EnregistrerService enregistrerService;

    @GetMapping
    @Operation(
            summary = "Liste toutes les entrées enregistrées",
            description = "Retourne une liste de toutes les entrées de l'entité Enregistrer.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des entrées récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<EnregistrerDto>> getAllEnregistrer() {
        return this.enregistrerService.getAllEnregistrer();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupère une entrée enregistrée par ID",
            description = "Retourne les détails d'une entrée de l'entité Enregistrer basée sur son ID.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entrée récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entrée non trouvée")
            }
    )
    public ResponseEntity<EnregistrerDto> getEnregistrerById(
            @Parameter(description = "ID de l'entrée à récupérer") @PathVariable Integer id) {
        return this.enregistrerService.getEnregistrerById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée une nouvelle entrée",
            description = "Ajoute une nouvelle entrée à l'entité Enregistrer.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Entrée créée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<EnregistrerDto> createEnregistrer(
            @Parameter(description = "Détails de l'entrée à créer") @RequestBody EnregistrerDto enregistrerDto) {
        return this.enregistrerService.createEnregistrer(enregistrerDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour une entrée",
            description = "Met à jour les informations d'une entrée basée sur son ID.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entrée mise à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entrée non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<EnregistrerDto> updateEnregistrer(
            @Parameter(description = "ID de l'entrée à mettre à jour") @PathVariable Integer id,
            @Parameter(description = "Nouvelles informations de l'entrée") @RequestBody EnregistrerDto enregistrerDto) {
        return this.enregistrerService.updateEnregistrer(id, enregistrerDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime une entrée",
            description = "Supprime une entrée de l'entité Enregistrer basée sur son ID.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Entrée supprimée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Entrée non trouvée")
            }
    )
    public ResponseEntity<Void> deleteEnregistrer(
            @Parameter(description = "ID de l'entrée à supprimer") @PathVariable Integer id) {
        return this.enregistrerService.deleteEnregistrer(id);
    }
}

