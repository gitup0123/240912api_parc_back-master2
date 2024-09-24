package com.parc.api.service;

import com.parc.api.security.CustomUserDetail;
import com.parc.api.model.entity.Utilisateur;
import com.parc.api.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Indique que cette classe est un service Spring, un composant qui peut être injecté dans d'autres parties de l'application.
@RequiredArgsConstructor // Génère un constructeur pour injecter automatiquement tous les champs "final", ici "utilisateurRepository".
public class UserLoaderService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository; // Dépendance injectée pour interagir avec la base de données et récupérer les utilisateurs.

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche un utilisateur par son email dans la base de données.
        Utilisateur utilisateur = this.utilisateurRepository.findByEmail(email)
                // Si aucun utilisateur n'est trouvé, une exception UsernameNotFoundException est levée.
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));

        // Retourne un objet CustomUserDetail qui implémente UserDetails et contient les informations de l'utilisateur.
        return new CustomUserDetail(utilisateur);
    }
}

