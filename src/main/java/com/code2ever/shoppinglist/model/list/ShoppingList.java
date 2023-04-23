package com.code2ever.shoppinglist.model.list;

import com.code2ever.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "shopping_list")
@AttributeOverride(name = "id", column = @Column(name = "Id_Shopping_List"))
public class ShoppingList extends AEntity {

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Total", nullable = false)
    private BigDecimal total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingList that)) return false;
        if (!super.equals(o)) return false;
        return name.equals(that.name) && total.equals(that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, total);
    }
}
