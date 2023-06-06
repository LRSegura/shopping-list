package com.code2ever.shoppinglist.repository.item;

import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT item FROM Item item WHERE item.id NOT IN (SELECT detail.item.id FROM DetailList detail " +
            "WHERE detail.shoppingList = :shoppingList )" )
    List<Item> getItemNotInShoppingList(ShoppingList shoppingList);

}