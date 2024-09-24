package com.parc.api.service;

import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.entity.Validation;
import com.parc.api.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

// Indique que cette classe est un service Spring qui peut être injecté dans d'autres composants.
@Service
// Génère automatiquement un constructeur avec tous les champs injectés en paramètre.
@AllArgsConstructor
public class ValidationService {

    // Dépendance pour accéder et interagir avec la base de données des validations.
    private ValidationRepository validationRepository;
    // Service utilisé pour envoyer des notifications, probablement par email ou autre canal.
    private NotificationService notificationService;

    // Enregistre une nouvelle validation pour un utilisateur donné.
    public void enregistrer(Utilisateur utilisateur) {
        Validation validation = new Validation(); // Crée une nouvelle instance de validation.
        validation.setUtilisateur(utilisateur); // Associe la validation à l'utilisateur.
        Instant creation = Instant.now(); // Récupère l'heure actuelle (date de création de la validation).
        validation.setCreation(creation); // Enregistre l'heure de création.
        // Définit la date d'expiration à 20 minutes après la date de création.
        Instant expiration = creation.plus(20, MINUTES);
        validation.setExpire(expiration);
        // Génère un code de validation aléatoire de 7 chiffres.
        Random random = new Random();
        int randomInt = random.nextInt(9999999);
        String code = String.format("%06d", randomInt); // Format le code pour qu'il soit toujours sur 6 chiffres (ajoute des zéros au besoin).
        validation.setCode(code); // Associe ce code à l'instance de validation.
        // Sauvegarde la validation dans la base de données via le repository.
        this.validationRepository.save(validation);
        // Utilise le service de notification pour envoyer le code de validation à l'utilisateur (probablement via email ou SMS).
        this.notificationService.envoyer(validation);
    }
    // Recherche et retourne une validation à partir de son code.
    public Validation LireEnFonctionDuCode(String code) {
        // Vérifie si le code est nul ou vide, lève une exception si c'est le cas.
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty"); // Erreur si le code est absent.
        }
        try {
            // Recherche dans la base de données une validation associée à ce code.
            return this.validationRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("Code valide not found")); // Lève une exception si le code n'est pas trouvé.
        } catch (RuntimeException e) {
            // Capture toute exception inattendue et la relance avec un message plus générique.
            throw new RuntimeException("An unexpected error occurred", e); // Gestion des erreurs imprévues.
        }
    }
}

