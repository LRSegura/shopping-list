package com.lab.shoppinglist.model;

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
    private LocalDate registerDate;
}
