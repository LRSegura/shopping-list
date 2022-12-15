package com.lab.shoppinglist.model.list;

import com.lab.shoppinglist.api.annotations.Description;
import com.lab.shoppinglist.api.annotations.InjectedDate;
import com.lab.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "shopping_list")
public class ShoppingList extends AEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "register_date",nullable = false)
    @InjectedDate
    @Description("Register Date")
    private LocalDate registerDate;
}
