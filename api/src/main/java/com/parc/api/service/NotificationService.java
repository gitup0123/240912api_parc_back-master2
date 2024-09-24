package com.parc.api.service;

import com.parc.api.model.entity.Validation;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

// Annotation Lombok pour générer un constructeur avec tous les arguments,
// ce qui permet d'injecter des dépendances par le constructeur
@AllArgsConstructor
@Service // Indique à Spring que cette classe est un service et qu'elle doit être gérée par le conteneur de dépendances
public class NotificationService {
    // Création d'un logger pour enregistrer des messages d'erreur ou d'information dans les logs
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    // JavaMailSender est une interface utilisée pour envoyer des emails avec Spring
    private final JavaMailSender javaMailSender;
    // Méthode pour envoyer un email de notification de validation
    public void envoyer(Validation validation) {
        try {
            // Création d'un objet MimeMessage pour représenter un email MIME (avec HTML, pièces jointes, etc.)
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // MimeMessageHelper simplifie la création d'emails MIME complexes
            // Le second argument 'true' permet d'indiquer qu'on va envoyer du contenu HTML
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // Définir l'adresse email de l'expéditeur
            helper.setFrom("ParcAdvisor@parc.com");
            // Récupérer l'adresse email de l'utilisateur à partir de l'objet Validation
            helper.setTo(validation.getUtilisateur().getEmail());
            // Définir le sujet de l'email
            helper.setSubject("Votre code d'activation - ParcAdvisor");
            // Création du contenu HTML de l'email avec des informations personnalisées
            // On utilise String.format pour insérer dynamiquement des valeurs dans le modèle HTML
            String htmlContent = String.format(
                    "<html>" +
                            "<body>" +
                            "<h2>Bonjour %s,</h2>" +  // Insertion du pseudo de l'utilisateur dans la balise <h2>
                            "<p>Votre code d'activation est <strong>%s</strong>.</p>" +  // Insertion du code d'activation
                            "<p>Cliquez sur le lien ci-dessous pour activer votre compte :</p>" +
                            // Lien personnalisé avec le code d'activation
                            "<p><a href=\"https://example.com/activate/%s\">Activer mon compte</a></p>" +
                            "<p>A bientôt,<br> L'équipe ParcAdvisor</p>" +
                            "</body>" +
                            "</html>",
                    validation.getUtilisateur().getPseudo(),  // Remplace %s par le pseudo de l'utilisateur
                    validation.getCode(),                     // Remplace %s par le code d'activation
                    validation.getCode()                      // Réutilisation du code pour générer le lien d'activation
            );
            // Définir le contenu de l'email avec le HTML généré
            helper.setText(htmlContent, true); // Le 'true' signifie que c'est du contenu HTML
            // Envoi de l'email à l'utilisateur
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            // En cas d'exception lors de l'envoi de l'email, on capture l'exception et on la consigne dans les logs
            // Logger permet une meilleure gestion des erreurs que System.err, notamment dans un environnement de production
            logger.error("Error sending email to {}: {}", validation.getUtilisateur().getEmail(), e.getMessage());
        }
    }
}
