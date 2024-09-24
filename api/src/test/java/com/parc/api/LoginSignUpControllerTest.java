package com.parc.api;

import com.parc.api.controller.LoginSignUpController;
import com.parc.api.model.dto.AuthentificationDTO;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.repository.UtilisateurRepository;
import com.parc.api.service.JwtService;
import com.parc.api.service.TokenBlacklistService;
import com.parc.api.service.UtilisateurService;
import com.parc.api.service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LoginSignUpControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private ValidationService validationService;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @InjectMocks
    private LoginSignUpController loginSignUpController;

    @Test
    void testInscription() {
        // Arrange
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setPseudo("pseudo59");
        utilisateurDto.setEmail("email@example.com");
        utilisateurDto.setMdp("mdp1234");

        UtilisateurDto createdUserDto = new UtilisateurDto();
        createdUserDto.setPseudo("pseudo59");
        createdUserDto.setEmail("email@example.com");
        createdUserDto.setMdp("mdp1234");

        // Configure the mock to return a UtilisateurDto
        when(utilisateurService.createUtilisateur(any(UtilisateurDto.class)))
                .thenReturn(new ResponseEntity<>(createdUserDto, HttpStatus.CREATED));

        // Act
        ResponseEntity<UtilisateurDto> responseEntity = loginSignUpController.inscription(utilisateurDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdUserDto, responseEntity.getBody());  // Compare the objects
    }

    @Test
    void testConnexionSuccess() {
        // Arrange
        AuthentificationDTO authentificationDTO = new AuthentificationDTO("email@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken("email@example.com")).thenReturn("dummyToken");

        // Act
        ResponseEntity<Map<String, String>> responseEntity = loginSignUpController.connexion(authentificationDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Map.of("token", "dummyToken"), responseEntity.getBody());
    }

    @Test
    void testConnexionFailure() {
        // Arrange
        AuthentificationDTO authentificationDTO = new AuthentificationDTO("email@example.com", "wrongPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<Map<String, String>> responseEntity = loginSignUpController.connexion(authentificationDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Map.of("error", "les informations d'identifications sont invalides"), responseEntity.getBody());
    }

    @Test
    void testConnexionException() {
        // Arrange
        AuthentificationDTO authentificationDTO = new AuthentificationDTO("email@example.com", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<Map<String, String>> responseEntity = loginSignUpController.connexion(authentificationDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Map.of("error", "les informations d'identifications sont invalides"), responseEntity.getBody());
    }

    @Test
    void testDeconnexion_Success() {
        // Simuler un token et sa date d'expiration
        String token = "valid-token";
        Date expirationDate = new Date(System.currentTimeMillis() + 100000);

        // Simuler les appels du jwtService
        when(jwtService.extractTokenFromRequest(request)).thenReturn(token);
        when(jwtService.extractExpirationDate(token)).thenReturn(expirationDate);

        // Appeler la méthode de déconnexion
        ResponseEntity<String> response = loginSignUpController.deconnexion(request);

        // Vérifier que la méthode "blacklistToken" a été appelée avec le bon token et TTL
        long expectedTTL = expirationDate.getTime() - System.currentTimeMillis();
        verify(tokenBlacklistService, times(1)).blacklistToken(eq(token), anyLong());

        // Vérifier que la réponse est "Déconnexion réussie"
        assertEquals("Déconnexion réussie", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeconnexion_TokenAlreadyExpired() {
        // Simuler un token expiré
        String token = "expired-token";
        Date expirationDate = new Date(System.currentTimeMillis() - 100000); // Token expiré

        // Simuler les appels du jwtService
        when(jwtService.extractTokenFromRequest(request)).thenReturn(token);
        when(jwtService.extractExpirationDate(token)).thenReturn(expirationDate);

        // Appeler la méthode de déconnexion
        ResponseEntity<String> response = loginSignUpController.deconnexion(request);

        // Vérifier que `blacklistToken` n'a pas été appelée puisque le token est déjà expiré
        verify(tokenBlacklistService, never()).blacklistToken(anyString(), anyLong());

        // Vérifier que la réponse est toujours "Déconnexion réussie"
        assertEquals("Déconnexion réussie", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeconnexion_NullToken() {
        // Simuler un cas où aucun token n'est présent dans la requête
        when(jwtService.extractTokenFromRequest(request)).thenReturn(null);

        // Appeler la méthode de déconnexion
        ResponseEntity<String> response = loginSignUpController.deconnexion(request);

        // Vérifier que `blacklistToken` n'a pas été appelée
        verify(tokenBlacklistService, never()).blacklistToken(anyString(), anyLong());

        // Vérifier que la réponse est "Déconnexion réussie" malgré l'absence de token
        assertEquals("Déconnexion réussie", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

}
