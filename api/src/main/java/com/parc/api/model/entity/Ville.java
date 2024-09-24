package com.parc.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ville", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nom_ville", nullable = false, length = 50)
    private String nomVille;

    @Size(max = 5)
    @NotNull
    @Column(name = "code_postal", nullable = false, length = 5)
    private String codePostal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_region", nullable = false)
    private Region idRegion;

}