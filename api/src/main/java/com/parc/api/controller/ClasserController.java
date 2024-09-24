package com.parc.api.controller;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.service.ClasserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/classer")
@Tag(name = "classer", description = "Opérations liées aux classes")
public class ClasserController {

    private final ClasserService classerService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste de la table classer",
            description = "Retourne une liste de tous les classer.",
            operationId = "getAllClassers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des classer récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ClasserDto>> getAllClasser() {
        return (ResponseEntity<List<ClasserDto>>) this.classerService.getAllClassers();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche un classer par ID",
            description = "Retourne les détails d'un classer basé sur son ID.",
            operationId = "getClasserById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Classer trouvé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Classer non trouvé")
            }
    )
    public ResponseEntity<ClasserDto> getClasserById(
            @Parameter(description = "ID du classer à rechercher") @PathVariable Integer id) {
        Optional<ClasserDto> classerDto = this.classerService.getClasserById(id);
        return classerDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @PostMapping
    @Operation(
            summary = "Crée un nouveau classer",
            description = "Ajoute un nouveau classer à la base de données.",
            operationId = "createClasser",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Classer créé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ClasserDto> createClasser(
            @Parameter(description = "Détails du classer à créer") @RequestBody ClasserDto classerDto) {
        ClasserDto createdClasser = this.classerService.createClasser(classerDto);
        return ResponseEntity.status(201).body(createdClasser);
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour un classer",
            description = "Met à jour les informations d'un classer existant.",
            operationId = "updateClasser",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Classer mis à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Classer non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ClasserDto> updateClasser(
            @Parameter(description = "ID du classer à mettre à jour") @PathVariable Integer id,
            @Parameter(description = "Nouvelles informations du classer") @RequestBody ClasserDto classerDto) {
        Optional<ClasserDto> updatedClasser = this.classerService.updateClasser(id, classerDto);
        return updatedClasser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime un classer",
            description = "Supprime un classer basé sur son ID.",
            operationId = "deleteClasser",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Classer supprimé avec succès"
                    ),
                    @ApiResponse(responseCode = "404", description = "Classer non trouvé")
            }
    )
    public ResponseEntity<Void> deleteClasser(
            @Parameter(description = "ID du classer à supprimer") @PathVariable Integer id) {
        boolean isDeleted = this.classerService.deleteClasser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}