package com.code2ever.shoppinglist.repository.detail;

import com.code2ever.shoppinglist.model.detail.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DetailListRepository extends JpaRepository<DetailList,Long> {

    List<DetailList> findDetailListByShoppingList(ShoppingList shoppingList);

    List<DetailList> findDetailListByShoppingListAndBought(ShoppingList shoppingList, Boolean bought);

    @Query(value = "SELECT sum(total) FROM DetailList where shoppingList = :shoppingList")
    BigDecimal getTotalPriceByShoppingList(ShoppingList shoppingList);

    Integer countAllByShoppingList(ShoppingList shoppingList);
}