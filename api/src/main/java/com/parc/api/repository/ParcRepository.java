package com.parc.api.repository;


import com.parc.api.model.entity.Parc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParcRepository extends JpaRepository<Parc, Integer> {
    Optional<Parc> findParcByNomParc(String nomParc);
}
