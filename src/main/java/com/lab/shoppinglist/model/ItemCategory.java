package com.lab.shoppinglist.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item_category")
public class ItemCategory extends AEntity {

    @Column(nullable = false, unique = true)
    private String description;

}
