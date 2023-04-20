package com.code2ever.shoppinglist.model.category;

import com.code2ever.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Category")
@AttributeOverride(name = "id", column = @Column(name = "Id_Category"))
public class Category extends AEntity {

    @Column(name = "Description", nullable = false, unique = true)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category that)) return false;
        if (!super.equals(o)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
