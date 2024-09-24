package com.parc.api.controller;

import com.parc.api.model.dto.TypeParcDto;
import com.parc.api.service.TypeParcService;
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
@RequestMapping("/type-parc")
@Tag(name = "type-parc", description = "Opérations sur les types de parc")
public class TypeParcController {

    private final TypeParcService typeParcService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des types de parc",
            description = "Retourne une liste de tous les types de parc.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des types de parc retournée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<TypeParcDto>> getTypeParc() {
        return this.typeParcService.getTypeParc();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche un type de parc par ID",
            description = "Retourne un type de parc basé sur son ID.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Type de parc trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "404", description = "Type de parc non trouvé")
            })
    public ResponseEntity<TypeParcDto> getTypeParcById(@PathVariable Integer id) {
        return this.typeParcService.getTypeParcById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée un nouveau type de parc",
            description = "Ajoute un nouveau type de parc à la base de données.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Type de parc créé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            })
    public ResponseEntity<TypeParcDto> createTypeParc(@RequestBody TypeParcDto typeParcDto) {
        return this.typeParcService.createTypeImage(typeParcDto);
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour un type de parc",
            description = "Met à jour les informations d'un type de parc existant basé sur son ID.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Type de parc mis à jour", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "404", description = "Type de parc non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            })
    public ResponseEntity<TypeParcDto> updateTypeParc(@PathVariable Integer id, @RequestBody TypeParcDto typeParcDto) {
        return this.typeParcService.updateTypeImage(id, typeParcDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un type de parc",
            description = "Supprime un type de parc basé sur son ID.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Type de parc supprimé"),
                    @ApiResponse(responseCode = "404", description = "Type de parc non trouvé")
            })
    public ResponseEntity<Void> deleteTypeParc(@PathVariable Integer id) {
        return this.typeParcService.deleteTypeImage(id);
    }
}

