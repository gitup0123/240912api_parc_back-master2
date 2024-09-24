package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classer")
public class Classer {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ClasserId id;

    @MapsId("idParc")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

    @MapsId("idTypeParc")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_parc", nullable = false)
    private TypeParc idTypeParc;

}