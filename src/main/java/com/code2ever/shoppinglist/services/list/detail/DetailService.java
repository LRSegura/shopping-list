package com.code2ever.shoppinglist.services.list.detail;

import com.code2ever.shoppinglist.model.list.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.list.detail.DetailListRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.services.list.AService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class DetailService extends AService {

    private final List<DetailList> itemDetailListToBuy;

    private final List<DetailList> itemDetailListBought;

    private final DetailListRepository repository;

    private final ListRepository listRepository;

    public DetailService(DetailListRepository repository, ListRepository listRepository) {
        this.repository = repository;
        itemDetailListToBuy = new ArrayList<>();
        itemDetailListBought = new ArrayList<>();
        this.listRepository = listRepository;
    }

    public List<DetailList> findDetailListToBuy(){
        return null;
    }

    public List<DetailList> findDetailListByShoppingListToBuy(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        itemDetailListToBuy.clear();
        itemDetailListToBuy.addAll(repository.findDetailListByShoppingListAndBought(shoppingList, false));
        return itemDetailListToBuy;
    }

    public List<DetailList> findDetailListByShoppingListBought(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        itemDetailListBought.clear();
        itemDetailListBought.addAll(repository.findDetailListByShoppingListAndBought(shoppingList, true));
        return itemDetailListBought;
    }

    private ShoppingList getShoppingListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Error removing list"));
    }

    public List<DetailList> getItemDetailListToBuy() {
        return itemDetailListToBuy;
    }

    public List<DetailList> getItemDetailListBought() {
        return itemDetailListBought;
    }

    @Override
    protected ListRepository getListRepository() {
        return listRepository;
    }

    @Override
    protected DetailListRepository getDetailListRepository() {
        return repository;
    }

    @Transactional
    public void buyItem(String idDetail){
        Long id = Long.parseLong(idDetail);
        Predicate<DetailList> filter = detailList -> detailList.getId().equals(id);
        itemDetailListToBuy.stream().filter(filter).forEach(detailList -> {
            detailList.setBought(true);
            itemDetailListBought.add(detailList);
            repository.save(detailList);
        });
        itemDetailListToBuy.removeIf(filter);
    }

    @Transactional
    public void cancel(String idDetail){
        Long id = Long.parseLong(idDetail);
        Predicate<DetailList> filter = detailList -> detailList.getId().equals(id);
        itemDetailListBought.stream().filter(filter).forEach(detailList -> {
            detailList.setBought(false);
            itemDetailListToBuy.add(detailList);
            repository.save(detailList);
        });
        itemDetailListBought.removeIf(filter);
    }
}