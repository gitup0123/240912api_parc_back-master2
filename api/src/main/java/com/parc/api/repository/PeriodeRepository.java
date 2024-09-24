package com.parc.api.repository;

import com.parc.api.model.entity.Periode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodeRepository extends JpaRepository<Periode, Integer> {
}
