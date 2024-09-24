package com.parc.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reseau_sociaux")
public class ReseauxSociaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reseau_sociaux", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "lib_reseau_sociaux", nullable = false, length = 50)
    private String libReseauSociaux;

}