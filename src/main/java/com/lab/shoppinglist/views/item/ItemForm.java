package com.lab.shoppinglist.views.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ItemForm {

    private String name;
    private BigDecimal price;
    private Long itemCategory;
}
