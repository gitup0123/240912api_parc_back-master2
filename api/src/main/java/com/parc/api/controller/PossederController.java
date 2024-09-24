package com.parc.api.controller;

import com.parc.api.model.dto.PossederDto;
import com.parc.api.service.PossederService;
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
@RequestMapping("/posseder")
@Tag(name = "posseder", description = "API pour gérer les associations entre réseaux sociaux et parcs")
public class PossederController {

    private final PossederService possederService; ;

    @GetMapping
    @Operation(
            summary = "Obtenir toutes les associations",
            description = "Retourne une liste de toutes les associations entre réseaux sociaux et parcs.",
            operationId = "posseder",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des associations récupérée avec succès"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<PossederDto>> findAll() {
        return this.possederService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PossederDto> getPossederById(@PathVariable Integer id) {
        return this.possederService.getPossederById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrateur')")
    public ResponseEntity<PossederDto> createPosseder(@RequestBody PossederDto possederDto) {
        return this.possederService.createPosseder(possederDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    public ResponseEntity<PossederDto> updatePosseder(@PathVariable Integer id, @RequestBody PossederDto possederDto) {
        return this.possederService.updatePosseder(id, possederDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrateur')")
    public ResponseEntity<Void> deletePosseder(@PathVariable Integer id) {
        return this.possederService.deletePosseder(id);
    }


}
