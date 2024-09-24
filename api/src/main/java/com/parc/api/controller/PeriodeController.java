package com.parc.api.controller;

import com.parc.api.model.dto.PeriodeDto;
import com.parc.api.service.PeriodeService;
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
@RequestMapping("/periodes")
@Tag(name = "periodes", description = "Opérations sur les périodes")
public class PeriodeController {

    private final PeriodeService periodeService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des périodes",
            description = "Retourne une liste de toutes les périodes.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des périodes retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<PeriodeDto>> getAllPeriode() {
        return this.periodeService.getAllPeriode();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche une période par ID",
            description = "Retourne une période basée sur son ID.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Période trouvée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Période non trouvée")
            }
    )
    public ResponseEntity<PeriodeDto> getPeriodeById(
            @Parameter(description = "ID de la période à récupérer") @PathVariable int id) {
        return this.periodeService.getPeriodeById(id);
    }

    @PostMapping("/parcs/{idParc}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée une nouvelle période",
            description = "Ajoute une nouvelle période à la base de données.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Période créée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<PeriodeDto> createPeriodeByParc(
            @Parameter(description = "Détails de la période à créer") @RequestBody PeriodeDto periodeDto) {
        return this.periodeService.createPeriodeByParc(periodeDto);
    }

    @PutMapping("/parcs/{idParc}/periodes/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour une période",
            description = "Met à jour les informations d'une période existante basée sur son ID.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Période mise à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Période non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<PeriodeDto> updatePeriode(
            @Parameter(description = "ID de la période à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations de la période") @RequestBody PeriodeDto periodeDto) {
        return this.periodeService.updatePeriode(periodeDto, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime une période",
            description = "Supprime une période basée sur son ID.",
            operationId = "periode",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Période supprimée"),
                    @ApiResponse(responseCode = "404", description = "Période non trouvée")
            }
    )
    public ResponseEntity<Void> deletePeriode(
            @Parameter(description = "ID de la période à supprimer") @PathVariable int id) {
        return this.periodeService.deletePeriode(id);
    }
}

