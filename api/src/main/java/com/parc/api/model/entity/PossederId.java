package com.parc.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PossederId implements Serializable {

    @Serial
    private static final long serialVersionUID = -1401902753006990431L;

    @NotNull
    @Column(name = "id_reseau_sociaux", nullable = false)
    private Integer idReseauSociaux;

    @NotNull
    @Column(name = "id_parc", nullable = false)
    private Integer idParc;

    public PossederId() {}

    public PossederId(Integer idReseauSociaux, Integer idParc) {
        this.idReseauSociaux = idReseauSociaux;
        this.idParc = idParc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PossederId that = (PossederId) o;
        return Objects.equals(idReseauSociaux, that.idReseauSociaux) &&
                Objects.equals(idParc, that.idParc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReseauSociaux, idParc);
    }
}