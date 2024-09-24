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
public class ClasserId implements java.io.Serializable {
    private static final long serialVersionUID = 4493635307049167951L;
    @NotNull
    @Column(name = "id_parc", nullable = false)
    private Integer idParc;

    @NotNull
    @Column(name = "id_type_parc", nullable = false)
    private Integer idTypeParc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClasserId entity = (ClasserId) o;
        return Objects.equals(this.idTypeParc, entity.idTypeParc) &&
                Objects.equals(this.idParc, entity.idParc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeParc, idParc);
    }

}