package com.code2ever.shoppinglist.model.item;


import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
import com.code2ever.shoppinglist.api.rest.item.JsonItem;
import com.code2ever.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "Item")
@AttributeOverride(name = "id", column = @Column(name = "Id_Item"))
public class Item extends AEntity {

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "Id_Category", nullable = false)
    private Category category;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        if (!super.equals(o)) return false;
        return name.equals(item.name) && price.equals(item.price) && category.equals(item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price, category);
    }
}
