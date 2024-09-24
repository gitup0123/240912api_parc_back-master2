package com.parc.api.service;

import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.entity.Validation;
import com.parc.api.model.mapper.UtilisateurMapper;
import com.parc.api.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor // Génère automatiquement un constructeur avec tous les champs "final" injectés (ici les repositories et services nécessaires).
@Service // Indique que cette classe est un service Spring, un composant injectable.
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository; // Accès aux opérations de la base de données pour les utilisateurs.
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // Utilisé pour chiffrer les mots de passe avant de les stocker en base de données.
    private final ValidationService validationService; // Service utilisé pour la gestion de la validation (activation des utilisateurs).

    // Récupération de tous les utilisateurs en tant que liste de DTOs.
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateur() {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll(); // Récupère tous les utilisateurs depuis la base de données.
        List<UtilisateurDto> utilisateurDtos = utilisateurList.stream()
                .map(UtilisateurMapper::toDto).toList(); // Convertit la liste des entités Utilisateur en liste de DTOs à l'aide d'un mapper.
        return ResponseEntity.ok(utilisateurDtos); // Retourne la liste avec le statut HTTP 200 OK.
    }

    // Récupération d'un utilisateur par son identifiant (ID).
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Integer id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id); // Cherche l'utilisateur par ID dans la base de données.
        if (utilisateur.isPresent()) {
            UtilisateurDto utilisateurDto = UtilisateurMapper.toDto(utilisateur.get()); // Si l'utilisateur est trouvé, convertit l'entité en DTO.
            return ResponseEntity.ok(utilisateurDto); // Retourne l'utilisateur avec le statut HTTP 200 OK.
        } else {
            return ResponseEntity.notFound().build(); // Si aucun utilisateur n'est trouvé, retourne une réponse avec le statut HTTP 404 Not Found.
        }
    }

    // Création d'un nouvel utilisateur.
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        // Vérifie si un utilisateur avec cet email existe déjà dans la base de données.
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateurDto.getEmail());
        if (utilisateurOptional.isPresent()) {
            // Si un email existe déjà, une exception est levée avec le statut HTTP 409 Conflict.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà existant");
        }
        // Validation du format de l'email.
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Expression régulière pour valider le format de l'email.
        if (!utilisateurDto.getEmail().matches(emailRegex)) {
            // Si l'email n'est pas valide, une exception est levée avec le statut HTTP 400 Bad Request.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format de l'email invalide");
        }
        // Validation du mot de passe : doit contenir une majuscule, un chiffre et un caractère spécial.
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!utilisateurDto.getMdp().matches(passwordRegex)) {
            // Si le mot de passe ne correspond pas à ces critères, une exception est levée avec le statut HTTP 400 Bad Request.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le mot de passe doit contenir au moins une majuscule, un chiffre, un caractère spécial et être d'une longueur minimale de 8 caractères.");
        }
        // Chiffrement du mot de passe.
        String mdpCrypte = this.bCryptPasswordEncoder.encode(utilisateurDto.getMdp()); // Chiffre le mot de passe avant de le stocker.
        utilisateurDto.setMdp(mdpCrypte); // Met à jour le DTO avec le mot de passe chiffré.
        // Convertit le DTO en entité.
        Utilisateur utilisateur = UtilisateurMapper.toEntity(utilisateurDto);
        // Sauvegarde l'utilisateur dans la base de données.
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        // Enregistre une validation ou envoie un mail.
        this.validationService.enregistrer(savedUtilisateur);
        // Convertit l'entité sauvegardée en DTO et retourne la réponse avec le statut HTTP 201 Created.
        UtilisateurDto savedUtilisateurDto = UtilisateurMapper.toDto(savedUtilisateur);
        return new ResponseEntity<>(savedUtilisateurDto, HttpStatus.CREATED);
    }


    // Suppression d'un utilisateur par son identifiant (ID).
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id); // Vérifie si l'utilisateur existe.
        if (utilisateurOptional.isPresent()) {
            utilisateurRepository.deleteById(id); // Supprime l'utilisateur si trouvé.
            return ResponseEntity.noContent().build(); // Retourne un statut HTTP 204 No Content si la suppression est réussie.
        } else {
            return ResponseEntity.notFound().build(); // Retourne un statut HTTP 404 Not Found si l'utilisateur n'est pas trouvé.
        }
    }

    // Mise à jour des informations d'un utilisateur existant.
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurDto utilisateurDto) {
        Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(id); // Vérifie si l'utilisateur existe.
        if (existingUtilisateur.isPresent()) {
            String mdpCrypte = this.bCryptPasswordEncoder.encode(utilisateurDto.getMdp()); // Chiffre le nouveau mot de passe avant de le stocker.
            utilisateurDto.setMdp(mdpCrypte);

            Utilisateur utilisateur = existingUtilisateur.get(); // Récupère l'entité Utilisateur existante.
            utilisateur.setPseudo(utilisateurDto.getPseudo()); // Met à jour les champs de l'utilisateur.
            utilisateur.setEmail(utilisateurDto.getEmail());
            utilisateur.setMdp(utilisateurDto.getMdp());
            utilisateur.setDateCreation(utilisateurDto.getDateCreation());
            utilisateur.setIsActive(utilisateurDto.getIsActive());

            // Sauvegarde l'utilisateur mis à jour dans la base de données.
            utilisateur = utilisateurRepository.save(utilisateur);

            // Retourne l'utilisateur mis à jour avec le statut HTTP 200 OK.
            return ResponseEntity.ok(UtilisateurMapper.toDto(utilisateur));
        } else {
            return ResponseEntity.notFound().build(); // Retourne un statut HTTP 404 Not Found si l'utilisateur n'est pas trouvé.
        }
    }

    // Activation d'un utilisateur via un code d'activation.
    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.LireEnFonctionDuCode(activation.get("code")); // Vérifie la validité du code d'activation.
        if (Instant.now().isAfter(validation.getExpire())) {
            throw new RuntimeException("votre code a expiré"); // Si le code a expiré, lève une exception.
        }
        // Récupère l'utilisateur à activer à partir du code d'activation.
        Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("utilisateur inconnu")); // Si l'utilisateur n'est pas trouvé, lève une exception.

        utilisateurActiver.setIsActive(true); // Active l'utilisateur.
        this.utilisateurRepository.save(utilisateurActiver); // Sauvegarde les changements dans la base de données.
    }
}
