package com.parc.api.controller;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.service.ImageService;
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
@RequestMapping("/image")
@Tag(name = "image", description = "Opérations liées aux images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Liste toutes les images",
            description = "Retourne une liste de toutes les images.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des images récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ImageDto>> getAllImages() {
        return this.imageService.getAllImages();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Récupère une image par ID",
            description = "Retourne les détails d'une image basée sur son ID.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Image non trouvée")
            }
    )
    public ResponseEntity<ImageDto> getImageById(
            @Parameter(description = "ID de l'image à récupérer") @PathVariable int id) {
        return this.imageService.getImageById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Crée une nouvelle image",
            description = "Ajoute une nouvelle image à la base de données.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Image créée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ImageDto> createImage(
            @Parameter(description = "Détails de l'image à créer") @RequestBody ImageDto imageDto, @PathVariable("idTypeImage") int idTypeImage, @PathVariable("idParc") int idParc) throws Exception{
        return this.imageService.createImage(imageDto, idTypeImage, idParc);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Met à jour une image",
            description = "Met à jour les informations d'une image basée sur son ID.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image mise à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Image non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ImageDto> updateImage(
            @Parameter(description = "ID de l'image à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations de l'image") @RequestBody ImageDto imageDto) {
        return this.imageService.updateImage(id, imageDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime une image",
            description = "Supprime une image basée sur son ID.",
            operationId = "image",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Image supprimée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Image non trouvée")
            }
    )
    public ResponseEntity<ImageDto> deleteImage(
            @Parameter(description = "ID de l'image à supprimer") @PathVariable int id) {
        return this.imageService.deleteImage(id);
    }
}
