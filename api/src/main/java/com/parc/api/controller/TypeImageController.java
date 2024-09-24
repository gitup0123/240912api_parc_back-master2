package com.parc.api.controller;


import com.parc.api.model.dto.TypeImageDto;
import com.parc.api.service.TypeImageService;
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
@RequestMapping("/type-image")
@Tag(name = "type-Image", description = "Opérations sur les types d'image")
public class TypeImageController {

    private final TypeImageService typeImageService;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des types d'image",
            description = "Retourne une liste de tous les types d'image.",
            operationId = "type-image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des types d'image retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<TypeImageDto>> getAllTypeImage() {
        return typeImageService.getAllTypeImage();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche un type d'image par ID",
            description = "Retourne un type d'image basé sur son ID.",
            operationId = "type-image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Type d'image trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Type d'image non trouvé")
            }
    )
    public ResponseEntity<TypeImageDto> getTypeImageById(@PathVariable Integer id) {
        return typeImageService.getTypeImageById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée un nouveau type d'image",
            description = "Ajoute un nouveau type d'image à la base de données.",
            operationId = "type-image",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Type d'image créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<TypeImageDto> createTypeImage(@RequestBody TypeImageDto typeImageDto) {
        return typeImageService.createTypeImage(typeImageDto);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour un type d'image",
            description = "Met à jour les informations d'un type d'image existant basé sur son ID.",
            operationId = "type-image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Type d'image mis à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Type d'image non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<TypeImageDto> updateTypeImage(@PathVariable Integer id, @RequestBody TypeImageDto typeImageDto) {
        return typeImageService.updateTypeImage(id, typeImageDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un type d'image",
            description = "Supprime un type d'image basé sur son ID.",
            operationId = "type-image",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Type d'image supprimé"),
                    @ApiResponse(responseCode = "404", description = "Type d'image non trouvé")
            }
    )
    public ResponseEntity<Void> deleteTypeImage(@PathVariable Integer id) {
        return typeImageService.deleteTypeImage(id);
    }
}

