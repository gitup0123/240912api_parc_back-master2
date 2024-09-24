package com.parc.api.controller;

import com.parc.api.model.dto.RegionDto;
import com.parc.api.service.RegionService;
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
@RequestMapping("/region")
@Tag(name = "region", description = "Opérations sur les régions")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des régions",
            description = "Retourne une liste de toutes les régions.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des régions retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        return this.regionService.getAllRegions();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche une région par ID",
            description = "Retourne une région basée sur son ID.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Région trouvée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Région non trouvée")
            }
    )
    public ResponseEntity<RegionDto> getRegionById(@PathVariable Integer id) {
        return this.regionService.getRegionById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée une nouvelle région",
            description = "Ajoute une nouvelle région à la base de données.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Région créée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<RegionDto> createRegionByPays(@RequestBody RegionDto regionDto) {
        return this.regionService.createRegionByPays(regionDto);
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour une région",
            description = "Met à jour les informations d'une région existante basée sur son ID.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Région mise à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Région non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<RegionDto> updateRegion(@PathVariable Integer id, @RequestBody RegionDto regionDto) {
        return this.regionService.updateRegion(id, regionDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime une région",
            description = "Supprime une région basée sur son ID.",
            operationId = "region",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Région supprimée"),
                    @ApiResponse(responseCode = "404", description = "Région non trouvée")
            }
    )
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        return this.regionService.deleteRegion(id);
    }
}



