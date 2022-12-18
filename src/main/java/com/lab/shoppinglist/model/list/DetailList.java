package com.lab.shoppinglist.model.list;

import com.lab.shoppinglist.model.AEntity;
import com.lab.shoppinglist.model.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "detail_list")
public class DetailList extends AEntity {

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Boolean bought;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;
}
