package com.code2ever.shoppinglist.model.detail;

import com.code2ever.shoppinglist.model.AEntity;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Detail_List")
@AttributeOverride(name = "id", column = @Column(name = "Id_Detail_List"))
public class DetailList extends AEntity {

    @ManyToOne
    @JoinColumn(name = "Id_item", nullable = false)
    private Item item;

    @Column(name = "Total", nullable = false)
    private BigDecimal total;

    @Column(name = "Amount", nullable = false)
    private Integer amount;

    @Column(name = "Bought", nullable = false)
    private Boolean bought;

    @ManyToOne
    @JoinColumn(name = "Id_Shopping_List")
    private ShoppingList shoppingList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailList that)) return false;
        if (!super.equals(o)) return false;
        return item.equals(that.item) && total.equals(that.total) && amount.equals(that.amount)
                && bought.equals(that.bought) && shoppingList.equals(that.shoppingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), item, total, amount, bought, shoppingList);
    }
}
