package com.parc.api.controller;

import com.parc.api.model.dto.AuthentificationDTO;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.service.JwtService;
import com.parc.api.service.TokenBlacklistService;
import com.parc.api.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification/Autorisation", description = "Opérations liées à l'authentification/autorisation")
public class LoginSignUpController {

    private AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    private final UtilisateurService utilisateurService;

    @PostMapping("/connexion")
    @Operation(summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT",
            operationId = "connexion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentification réussie", content = @Content(mediaType = "application/json"
                            , schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Informations d'identification invalides")
            })
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password())
            );
            if (authentication.isAuthenticated()) {
                log.info("Authentification réussie");
                String token = jwtService.generateToken(authentificationDTO.email());
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                log.info("Authentification échouée");
                return ResponseEntity.badRequest().body(Map.of("error", "les informations d'identifications sont invalides "));
            }
        } catch (BadCredentialsException e) {
            log.info("les informations d'identification invalides");
            return ResponseEntity.badRequest().body(Map.of("error", "les informations d'identifications sont invalides"));
        }
    }

    @PostMapping("/inscription")
    @Operation(summary = "Créer un utilisateur",
            description = "Crée un nouvel utilisateur",
            operationId = "inscription",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilisateur créé"
                            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurDto.class))),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            })
    public ResponseEntity<UtilisateurDto> inscription(@RequestBody UtilisateurDto utilisateurDto) {
        return this.utilisateurService.createUtilisateur(utilisateurDto);
    }

    @PostMapping("/activation")
    @Operation(summary = "Activer un utilisateur",
            description = "Active un utilisateur basé sur le code d'activation",
            operationId = "activation",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Activation réussie"),
                    @ApiResponse(responseCode = "400", description = "Code d'activation invalide ou expiré"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }

    @PostMapping("/deconnexion")
    @Operation(
            summary = "Déconnexion utilisateur",
            description = "Révoque le token JWT en l'ajoutant à la liste noire. Utilisé pour déconnecter un utilisateur.",
            operationId = "utilisateurs/deconnexion",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Déconnexion réussie. Le token a été révoqué."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erreur lors de la révoquation du token."
                    )
            }
    )
    public ResponseEntity<String> deconnexion(HttpServletRequest request) {
        String token = jwtService.extractTokenFromRequest(request);

        if (token != null) {
            Date expirationDate = jwtService.extractExpirationDate(token);
            long now = System.currentTimeMillis();
            long timeToLive = expirationDate.getTime() - now;

            if (timeToLive > 0) {
                tokenBlacklistService.blacklistToken(token, timeToLive);
            }
        }

        return ResponseEntity.ok("Déconnexion réussie");
    }
}
