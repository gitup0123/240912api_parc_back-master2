package com.parc.api.controller;
import com.parc.api.model.dto.PaysDto;

import com.parc.api.service.PaysService;
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
@RequestMapping("/pays")
@Tag(name = "pays", description = "Opérations sur les données des pays")
public class PaysController {

    private final PaysService paysService;

    @GetMapping
    @Operation(
            summary = "Liste tous les pays",
            description = "Retourne une liste de tous les pays.",
            operationId = "pays",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des pays récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaysDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<PaysDto>> getAllPays() {
        return this.paysService.getAllPays();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupère un pays par ID",
            description = "Retourne les détails d'un pays basé sur son ID.",
            operationId = "pays",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pays récupéré avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaysDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Pays non trouvé")
            }
    )
    public ResponseEntity<PaysDto> getPaysById(
            @Parameter(description = "ID du pays à récupérer") @PathVariable Integer id) {
        return this.paysService.getPaysById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée un nouveau pays",
            description = "Ajoute un nouveau pays à la base de données.",
            operationId = "pays",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pays créé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaysDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<PaysDto> createPays(
            @Parameter(description = "Détails du pays à créer") @RequestBody PaysDto paysDto) {
        return this.paysService.createPay(paysDto);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour un pays",
            description = "Met à jour les informations d'un pays basé sur son ID.",
            operationId = "pays",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pays mis à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaysDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Pays non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<PaysDto> updatePays(
            @Parameter(description = "ID du pays à mettre à jour") @PathVariable Integer id,
            @Parameter(description = "Nouvelles informations du pays") @RequestBody PaysDto paysDto) {
        return this.paysService.updatePays(id, paysDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un pays",
            description = "Supprime un pays basé sur son ID.",
            operationId = "pays",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pays supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Pays non trouvé")
            }
    )
    public ResponseEntity<Void> deletePays(
            @Parameter(description = "ID du pays à supprimer") @PathVariable Integer id) {
        return this.paysService.deletePay(id);
    }
}


