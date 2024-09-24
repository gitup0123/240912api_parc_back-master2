package com.parc.api;

import com.parc.api.controller.PaysController;
import com.parc.api.model.dto.PaysDto;
import com.parc.api.service.PaysService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Cette annotation permet d'intégrer l'extension Mockito avec JUnit 5 pour les tests.
public class PaysControllerTest {

    @Mock  // Cette annotation permet de créer une instance simulée (mock) de PaysService.
    private PaysService paysService;

    @InjectMocks  // InjectMocks injecte automatiquement les mocks dans l'objet testé ici, PaysController.
    private PaysController paysController;

    @Test  // Ce test vérifie le comportement de la méthode createPays du contrôleur PaysController.
    public void testCreatePays() {
        // Given : Initialisation des objets nécessaires pour le test.

        PaysDto paysDto = new PaysDto();  // Création d'un DTO de Pays.
        paysDto.setNomPays("France");  // On définit le nom du pays dans le DTO.

        // Simulation du comportement du service paysService pour la méthode createPay.
        // Lorsqu'on appelle paysService.createPay(paysDto), on simule le retour d'une réponse HTTP 201 (CREATED) avec le paysDto en corps de réponse.
        when(paysService.createPay(paysDto)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(paysDto));

        // Appel de la méthode createPays du contrôleur en passant le paysDto.
        ResponseEntity<PaysDto> response = paysController.createPays(paysDto);

        // Vérifications :
        assertNotNull(response);  // Vérifie que la réponse n'est pas null.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());  // Vérifie que le code de statut HTTP est 201 (CREATED).
        assertEquals("France", response.getBody().getNomPays());  // Vérifie que le nom du pays dans la réponse est bien "France".
    }

}
