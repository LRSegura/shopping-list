package com.code2ever.shoppinglist.services.list;

import com.code2ever.shoppinglist.model.list.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.repository.list.detail.DetailListRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class AService {

    private List<DetailList> itemDetailListToBuy = new ArrayList<>();

    private List<DetailList> itemDetailListBought = new ArrayList<>();

    private ShoppingList getShoppingListById(Long id) {
        return getListRepository().findById(id).orElseThrow(() -> new IllegalArgumentException("There is not a list with id: " + id));
    }

    public List<DetailList> findDetailListByShoppingListToBuy(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        itemDetailListToBuy.clear();
        itemDetailListToBuy.addAll(getDetailListRepository().findDetailListByShoppingListAndBought(shoppingList, false));
        return itemDetailListToBuy;
    }

    public List<DetailList> findDetailListByShoppingListBought(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        itemDetailListBought.clear();
        itemDetailListBought.addAll(getDetailListRepository().findDetailListByShoppingListAndBought(shoppingList, true));
        return itemDetailListBought;
    }

    public List<DetailList> getItemDetailListToBuy() {
        return itemDetailListToBuy;
    }

    public List<DetailList> getItemDetailListBought() {
        return itemDetailListBought;
    }

    protected abstract ListRepository getListRepository();

    protected abstract DetailListRepository getDetailListRepository();
}