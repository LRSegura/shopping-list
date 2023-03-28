package com.lab.shoppinglist.model.item;


import com.lab.shoppinglist.api.annotations.Description;
import com.lab.shoppinglist.api.annotations.InjectedDate;
import com.lab.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    @JoinColumn(name = "Id_Item_Category", nullable = false)
    private ItemCategory itemCategory;

    @Column(name = "register_date", nullable = false)
    @InjectedDate
    @Description("Register Date")
    private LocalDate registerDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        if (!super.equals(o)) return false;
        return name.equals(item.name) && price.equals(item.price) && itemCategory.equals(item.itemCategory)
                && registerDate.equals(item.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price, itemCategory, registerDate);
    }
}
