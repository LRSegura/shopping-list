package com.lab.shoppinglist.repository;

import com.lab.shoppinglist.model.list.DetailList;
import com.lab.shoppinglist.model.list.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetailListRepository extends JpaRepository<DetailList,Long> {

    List<DetailList> findDetailListByShoppingList(ShoppingList shoppingList);

    List<DetailList> findDetailListByShoppingListAndBought(ShoppingList shoppingList, Boolean bought);
}
