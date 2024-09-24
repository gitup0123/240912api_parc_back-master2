package com.parc.api.repository;


import com.parc.api.model.entity.Commentaire;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Utilisateur;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
    Optional<Commentaire> findByIdParcAndIdUtilisateur(@NotNull Parc idParc, @NotNull Utilisateur idUtilisateur);
}

