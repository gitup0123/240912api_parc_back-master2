package com.parc.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "enregister")
public class Enregistrer {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EnregistrerId id;

    @MapsId("idParc")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

    @MapsId("idUtilisateur")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur idUtilisateur;

    @NotNull
    @Column(name = "date_enregistrer", nullable = false)
    private LocalDate dateEnregistrer;

}