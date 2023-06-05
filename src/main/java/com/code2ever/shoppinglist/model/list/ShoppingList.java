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

    @Column(name = "Total_Price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "Total_Items", nullable = false)
    private Integer totalItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingList list)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, list.name) && Objects.equals(totalPrice, list.totalPrice) && Objects.equals(totalItems, list.totalItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, totalPrice, totalItems);
    }
}
