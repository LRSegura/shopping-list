package com.lab.shoppinglist.model.user;

import com.lab.shoppinglist.api.annotations.Description;
import com.lab.shoppinglist.api.annotations.InjectedDate;
import com.lab.shoppinglist.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@Table(name = "User_App")
@AttributeOverride(name = "id", column = @Column(name = "Id_User_App"))
public class UserApp extends AEntity {

    @Column(name = "First_Name", nullable = false)
    private String firstName;
    @Column(name = "Last_Name", nullable = false)
    private String lastName;
    @Column(name = "User_Name", nullable = false)
    private String userName;
    @Column(name = "Password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApp userApp)) return false;
        if (!super.equals(o)) return false;
        return firstName.equals(userApp.firstName) && lastName.equals(userApp.lastName) && userName.equals(userApp.userName)
                && password.equals(userApp.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, userName, password);
    }
}