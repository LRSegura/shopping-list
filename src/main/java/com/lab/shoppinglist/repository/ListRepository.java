package com.lab.shoppinglist.repository;

import com.lab.shoppinglist.model.list.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<ShoppingList, Long> {

    boolean existsByName(String name);
}
