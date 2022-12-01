package com.lab.shoppinglist.model;

import com.lab.shoppinglist.api.persistence.validations.HibernateEventHandlers;
import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@MappedSuperclass
@EntityListeners(HibernateEventHandlers.class)
public class AEntity implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AEntity aEntity = (AEntity) o;
        return id != null && Objects.equals(id, aEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
