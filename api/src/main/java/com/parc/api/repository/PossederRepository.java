package com.parc.api.repository;

import com.parc.api.model.entity.Posseder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PossederRepository extends JpaRepository<Posseder, Integer> {

    List<Posseder> findByIdParcId(Integer parcId);
}
