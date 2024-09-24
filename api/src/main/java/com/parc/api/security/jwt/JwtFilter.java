package com.parc.api.security.jwt;

import com.parc.api.service.JwtService;
import com.parc.api.service.TokenBlacklistService;
import com.parc.api.service.UserLoaderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // Services nécessaires pour la gestion des JWT, le chargement des utilisateurs et la vérification des tokens révoqués
    private final JwtService jwtService;
    private final UserLoaderService userLoaderService;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Récupère l'en-tête 'Authorization' de la requête HTTP
        String authorizationHeader = request.getHeader("Authorization");
        String email;
        String token;

        // Vérifie si l'en-tête 'Authorization' est présent et commence par 'Bearer '
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extrait le token en supprimant le préfixe 'Bearer '
            token = authorizationHeader.substring(7);

            // Vérifie si le token est présent dans la liste noire (token révoqué)
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                // Si le token est révoqué, renvoie un statut 403 Forbidden et interrompt le traitement de la requête
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Extrait l'email du token en utilisant le JwtService
            email = jwtService.extractEmail(token);

            // Vérifie si l'email extrait n'est pas null et si aucune authentification n'est déjà présente dans le contexte de sécurité
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Charge les détails de l'utilisateur en utilisant l'email extrait
                UserDetails userDetails = userLoaderService.loadUserByUsername(email);

                // Valide le token en s'assurant qu'il est bien formé et qu'il correspond à l'email extrait
                if (jwtService.validateToken(token, email)) {
                    // Crée un objet d'authentification avec les détails de l'utilisateur et les autorités associées
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    // Configure les détails supplémentaires de l'authentification (par exemple, l'adresse IP de la requête)
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Place l'objet d'authentification dans le contexte de sécurité de Spring, permettant l'accès aux ressources protégées
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        // Continue le traitement de la requête avec les filtres suivants de la chaîne
        filterChain.doFilter(request, response);
    }
}

