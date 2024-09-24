package com.parc.api.controller;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.service.CommentaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/commentaires")
@Tag(name = "commentaire", description = "Opérations sur les commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    @GetMapping
    @Operation(summary = "Affiche la liste des commentaires", description = "Retourne une liste de tous les commentaires.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des commentaires retournée"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<CommentaireDto>> getAllCommentaire() {
        return this.commentaireService.getAllCommentaire();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche un commentaire par ID",
            description = "Retourne un commentaire basé sur son ID.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Commentaire trouvé"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })
    public ResponseEntity<CommentaireDto> getCommentaireById(@PathVariable int id) {
        return this.commentaireService.getCommentaireById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('Utilisateur')")
    @Operation(
            summary = "Crée un commentaire par ID Utilisateur et ID Parc",
            description = "Ajoute un nouveau commentaire à la base de données.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Commentaire trouvé"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })

    public ResponseEntity<CommentaireDto> createCommentaire(@RequestBody CommentaireDto request) {
        return commentaireService.createCommentaireWithIds(
                request.getIdUtilisateur().getId(),
                request.getIdUtilisateur().getId(),
                request.getContenuCommentaire(),
                request.getNoteParc()
        );
    }


   /* @PostMapping
    @PreAuthorize("hasAuthority('Visiteur')")
    @PostMapping
    @PreAuthorize("hasAuthority('Utilisateur')")
    @Operation(summary = "Crée un nouveau commentaire",
            description = "Ajoute un nouveau commentaire à la base de données.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Commentaire créé"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            })
    public ResponseEntity<CommentaireDto> createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        return this.commentaireService.createCommentaire(commentaireDto);
    }*/

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Utilisateur')")
    @Operation(
            summary = "Met à jour un commentaire",
            description = "Met à jour les informations d'un commentaire existant basé sur son ID.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Commentaire mis à jour"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })
    public ResponseEntity<CommentaireDto> updateCommentaire(@PathVariable int id, @RequestBody CommentaireDto commentaireDto) {
        return this.commentaireService.updateCommentaire(id, commentaireDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    @Operation(
            summary = "Supprime un commentaire",
            description = "Supprime un commentaire basé sur son ID.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Commentaire supprimé"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })
    public ResponseEntity<CommentaireDto> deleteCommentaire(@PathVariable int id) {
        return this.commentaireService.deleteCommentaire(id);
    }
}
