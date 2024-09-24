package com.parc.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "parc")
public class Parc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parc", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nom_parc", nullable = false, length = 50)
    private String nomParc;

    @NotNull
    @Lob
    @Column(name = "presentation",columnDefinition = "TEXT", nullable = false)
    private String presentation;

    @Size(max = 228)
    @NotNull
    @Column(name = "adresse", nullable = false, length = 228)
    private String adresse;

    @NotNull
    @Column(name = "longitude_parc", nullable = false)
    private Double longitudeParc;

    @NotNull
    @Column(name = "latitude_parc", nullable = false)
    private Double latitudeParc;

    @Size(max = 250)
    @NotNull
    @Column(name = "site_internet", nullable = false, length = 250)
    private String siteInternet;

    @Size(max = 10)
    @NotNull
    @Column(name = "numero_tel_parc", nullable = false, length = 10)
    private String numeroTelParc;

    @NotNull
    @Column(name = "is_restauration", nullable = false)
    private Boolean isRestauration = false;

    @NotNull
    @Column(name = "is_boutique", nullable = false)
    private Boolean isBoutique = false;

    @NotNull
    @Column(name = "is_sejour", nullable = false)
    private Boolean isSejour = false;

    @NotNull
    @Column(name = "is_transport_commun", nullable = false)
    private Boolean isTransportCommun = false;

    @Size(max = 250)
    @Column(name = "url_affilation", length = 250)
    private String urlAffilation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ville", nullable = false)
    private Ville idVille;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parking", nullable = false)
    private Parking idParking;


}