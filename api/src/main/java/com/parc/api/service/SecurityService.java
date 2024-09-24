package com.parc.api.service;

import com.parc.api.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    public boolean isCurrentUserOrAdmin(Integer userId, Authentication authentication) {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        // Vérifie si l'utilisateur est un administrateur
        boolean isAdmin = userDetail.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Retourne true si l'utilisateur est soit l'utilisateur connecté, soit un administrateur
        return userDetail.getId() == userId || isAdmin;
    }
}

