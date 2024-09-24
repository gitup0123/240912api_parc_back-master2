package com.parc.api;

import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.entity.Validation;
import com.parc.api.repository.ValidationRepository;
import com.parc.api.service.NotificationService;
import com.parc.api.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationServiceTest {

    @Mock
    private ValidationRepository validationRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ValidationService validationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialisation des mocks
    }

    // Test de la méthode enregistrer
    @Test
    public void testEnregistrer() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur(); // Créer un utilisateur fictif
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        validation.setCreation(Instant.now());
        validation.setExpire(Instant.now().plus(20, MINUTES));
        validation.setCode("123456");

        // Simuler le comportement du repository et du service de notification
        when(validationRepository.save(any(Validation.class))).thenReturn(validation);

        // Act
        validationService.enregistrer(utilisateur);

        // Assert
        verify(validationRepository, times(1)).save(any(Validation.class));  // Vérifie que la méthode save a été appelée une fois
        verify(notificationService, times(1)).envoyer(any(Validation.class));  // Vérifie que la méthode envoyer a été appelée une fois
    }

    // Test de la méthode LireEnFonctionDuCode (cas positif)
    @Test
    public void testLireEnFonctionDuCode_Success() {
        // Arrange
        String code = "123456";
        Validation validation = new Validation();
        validation.setCode(code);

        // Simuler le comportement du repository
        when(validationRepository.findByCode(code)).thenReturn(Optional.of(validation));

        // Act
        Validation result = validationService.LireEnFonctionDuCode(code);

        // Assert
        assertNotNull(result);  // Vérifie que la validation n'est pas null
        assertEquals(code, result.getCode());  // Vérifie que le code est correct
    }

    /* Test de la méthode LireEnFonctionDuCode (code non trouvé)
    @Test
    public void testLireEnFonctionDuCode_NotFound() {
        // Arrange
        String code = "123456";

        // Simuler que le code n'existe pas dans la base de données
        when(validationRepository.findByCode(code)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validationService.LireEnFonctionDuCode(code);
        });

        assertEquals("Code valide not found", exception.getMessage());  // Vérifie que l'exception contient le bon message
    }*/

    // Test de la méthode LireEnFonctionDuCode (code null ou vide)
    @Test
    public void testLireEnFonctionDuCode_InvalidCode() {
        // Arrange
        String code = "   ";  // Code vide

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validationService.LireEnFonctionDuCode(code));

        assertEquals("Code cannot be null or empty", exception.getMessage());  // Vérifie que l'exception contient le bon message
    }
}
