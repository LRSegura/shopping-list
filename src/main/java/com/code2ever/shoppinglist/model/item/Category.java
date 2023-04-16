package com.code2ever.shoppinglist.model.item;

import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
import com.code2ever.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    public void update(JsonCategory category) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(category.id());
        if (Objects.nonNull(category.description())) {
            description = category.description();
        }
    }
}
