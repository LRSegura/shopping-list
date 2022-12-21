package com.lab.shoppinglist.model.user;

import com.lab.shoppinglist.api.annotations.Description;
import com.lab.shoppinglist.api.annotations.InjectedDate;
import com.lab.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User extends AEntity {

    @Column
    private String userName;
    @Column
    private String password;
    @Column
    @InjectedDate
    @Description("Register Date")
    private LocalDate registerDate;
}