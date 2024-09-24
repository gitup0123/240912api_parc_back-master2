package com.parc.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "periode")
public class Periode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periode", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "date_ouverture", nullable = false)
    private LocalDate dateOuverture;

    @NotNull
    @Column(name = "date_fermuture", nullable = false)
    private LocalDate dateFermuture;

    @NotNull
    @Column(name = "heure_ouverture", nullable = false)
    private LocalTime heureOuverture;

    @NotNull
    @Column(name = "heure_fermeture", nullable = false)
    private LocalTime heureFermeture;

    @NotNull
    @Column(name = "prix_adulte", nullable = false, precision = 15, scale = 3)
    private BigDecimal prixAdulte;

    @NotNull
    @Column(name = "prix_enfant", nullable = false, precision = 15, scale = 3)
    private BigDecimal prixEnfant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

}