package com.code2ever.shoppinglist.model;

import com.code2ever.shoppinglist.api.annotations.Description;
import com.code2ever.shoppinglist.api.annotations.InjectedDate;
import com.code2ever.shoppinglist.api.persistence.validations.HibernateEventHandlers;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    @Column(name = "Register_Date", nullable = false)
    @InjectedDate
    @Description("Register Date")
    private LocalDateTime registerDate;

    @Version
    @Column(name = "OptLock")
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AEntity aEntity)) return false;
        return version == aEntity.version && Objects.equals(id, aEntity.id) && Objects.equals(registerDate, aEntity.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registerDate, version);
    }
}
