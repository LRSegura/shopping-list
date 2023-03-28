package com.lab.shoppinglist.model.item;

import com.lab.shoppinglist.model.AEntity;
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
@Table(name = "Item_Category")
@AttributeOverride(name = "id", column = @Column(name = "Id_Item_Category"))
public class ItemCategory extends AEntity {

    @Column(name = "Description", nullable = false, unique = true)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCategory that)) return false;
        if (!super.equals(o)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
