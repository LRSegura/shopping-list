package com.lab.shoppinglist.model;

import com.lab.shoppinglist.api.persistence.validations.HibernateEventHandlers;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(HibernateEventHandlers.class)
public class AEntity implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AEntity aEntity)) return false;
        return id.equals(aEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
