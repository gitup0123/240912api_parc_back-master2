package com.parc.api.controller;

import com.parc.api.model.dto.ReseauxSociauxDto;
import com.parc.api.service.ReseauxSociauxService;
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
@RequestMapping("/reseaux-sociaux")
@Tag(name = "reseaux-sociaux", description = "Opérations sur les réseaux sociaux")
public class ReseauxSociauxController {

    private final ReseauxSociauxService reseauxSociauxService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des réseaux sociaux",
            description = "Retourne une liste de tous les réseaux sociaux.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des réseaux sociaux retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ReseauxSociauxDto>> getAllReseauxSociaux() {
        return this.reseauxSociauxService.getAllReseauxSociaux();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche un réseau social par ID",
            description = "Retourne un réseau social basé sur son ID.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Réseau social trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Réseau social non trouvé")
            }
    )
    public ResponseEntity<ReseauxSociauxDto> getReseauxSociauxById(@PathVariable Integer id) {
        return this.reseauxSociauxService.getReseauxSociauxById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée un nouveau réseau social",
            description = "Ajoute un nouveau réseau social à la base de données.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Réseau social créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ReseauxSociauxDto> createReseauxSociaux(@RequestBody ReseauxSociauxDto newReseauxSociauxDto) {
        return this.reseauxSociauxService.createReseauxSociaux(newReseauxSociauxDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour un réseau social",
            description = "Met à jour les informations d'un réseau social existant basé sur son ID.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Réseau social mis à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Réseau social non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ReseauxSociauxDto> updateReseauxSociaux(@PathVariable Integer id, @RequestBody ReseauxSociauxDto reseauxSociauxDto) {
        return this.reseauxSociauxService.updateReseauxSociaux(id, reseauxSociauxDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un réseau social",
            description = "Supprime un réseau social basé sur son ID.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Réseau social supprimé"),
                    @ApiResponse(responseCode = "404", description = "Réseau social non trouvé")
            }
    )
    public ResponseEntity<Void> deleteReseauxSociaux(@PathVariable Integer id) {
        return this.reseauxSociauxService.deleteReseauxSociaux(id);
    }
}


