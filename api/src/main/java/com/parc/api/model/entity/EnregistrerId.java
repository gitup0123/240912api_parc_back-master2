package com.parc.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EnregistrerId implements java.io.Serializable {
    private static final long serialVersionUID = 89316960164708118L;
    @NotNull
    @Column(name = "id_parc", nullable = false)
    private Integer idParc;

    @NotNull
    @Column(name = "id_utilisateur", nullable = false)
    private Integer idUtilisateur;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnregistrerId entity = (EnregistrerId) o;
        return Objects.equals(this.idUtilisateur, entity.idUtilisateur) &&
                Objects.equals(this.idParc, entity.idParc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateur, idParc);
    }

}