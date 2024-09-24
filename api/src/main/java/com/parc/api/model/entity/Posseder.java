package com.parc.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posseder")
public class Posseder {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PossederId id;

    @MapsId("idReseauSociaux")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reseau_sociaux", nullable = false)
    private ReseauxSociaux idReseauSociaux;

    @MapsId("idParc")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

    @Size(max = 250)
    @NotNull
    @Column(name = "url_reseau_sociaux", nullable = false, length = 250)
    private String urlReseauSociaux;

}