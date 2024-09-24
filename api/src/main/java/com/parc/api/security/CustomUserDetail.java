package com.parc.api.security;

import com.parc.api.model.entity.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor // Génère automatiquement un constructeur avec tous les champs "final" injectés (ici "utilisateur").
public class CustomUserDetail implements UserDetails {

    private final Utilisateur utilisateur; // Référence à l'objet "Utilisateur" qui contient les informations de l'utilisateur

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retourne la liste des autorités (ou rôles) associées à cet utilisateur.
        // Ici, un seul rôle est assigné à l'utilisateur en fonction de son "IdRole" provenant de la classe Utilisateur.
        return Collections.singletonList(new SimpleGrantedAuthority(utilisateur.getIdRole().getLibRole()));
    }
    @Override
    public String getPassword() {
        // Retourne le mot de passe de l'utilisateur stocké dans l'objet "Utilisateur".
        return utilisateur.getMdp();
    }
    @Override
    public String getUsername() {
        // Retourne l'email de l'utilisateur, qui est utilisé comme nom d'utilisateur pour l'authentification.
        return utilisateur.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        // Retourne true, indiquant que le compte n'a pas expiré. Cette méthode peut être personnalisée
        // pour renvoyer false si une logique d'expiration de compte est implémentée.
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // Retourne true, indiquant que le compte n'est pas verrouillé. Cela peut être modifié
        // si tu mets en place une logique pour verrouiller un compte après plusieurs tentatives de connexion échouées.
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // Retourne true, indiquant que les informations d'identification (mot de passe) n'ont pas expiré.
        // Cette méthode peut être personnalisée selon une politique d'expiration des mots de passe.
        return true;
    }
    @Override
    public boolean isEnabled() {
        // Vérifie si l'utilisateur est actif en renvoyant la valeur de "isActive" provenant de l'objet "Utilisateur".
        // Si "isActive" est true, l'utilisateur est autorisé à se connecter, sinon il est désactivé.
        return utilisateur.getIsActive();
    }

    public int getId() {
        return utilisateur.getId();
    }
}

