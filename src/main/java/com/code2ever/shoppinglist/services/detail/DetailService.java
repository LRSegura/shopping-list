package com.code2ever.shoppinglist.services.detail;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.detail.JsonDetail;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.list.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.repository.detail.DetailListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class DetailService implements RestCrudOperations<JsonDetail> {

    private final List<DetailList> itemDetailListToBuy;

    private final List<DetailList> itemDetailListBought;

    private final DetailListRepository repository;

    private final ItemRepository itemRepository;

    private final ListRepository listRepository;

    public DetailService(DetailListRepository repository, ItemRepository itemRepository, ListRepository listRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        itemDetailListToBuy = new ArrayList<>();
        itemDetailListBought = new ArrayList<>();
        this.listRepository = listRepository;
    }

    public List<DetailList> findDetailListToBuy() {
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


    @Transactional
    public void buyItem(String idDetail) {
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
    public void cancel(String idDetail) {
        Long id = Long.parseLong(idDetail);
        Predicate<DetailList> filter = detailList -> detailList.getId().equals(id);
        itemDetailListBought.stream().filter(filter).forEach(detailList -> {
            detailList.setBought(false);
            itemDetailListToBuy.add(detailList);
            repository.save(detailList);
        });
        itemDetailListBought.removeIf(filter);
    }

    @Override
    public List<? extends JsonData> restGet() {
        return repository.findAll().stream().map(detail -> new JsonDetail(detail.getId(), detail.getItem().getId(), detail.getAmount(), detail.getBought(), detail.getShoppingList().getId())).toList();
    }

    public List<? extends JsonData> restGet(Long idList) {
        ShoppingList shoppingList = getShoppingListById(idList);
        return repository.findDetailListByShoppingList(shoppingList).stream().map(detail -> new JsonDetail(detail.getId(), detail.getItem().getId(), detail.getAmount(), detail.getBought(), detail.getShoppingList().getId())).toList();
    }

    private ShoppingList getShoppingListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no list with " + "the id " + id));
    }

    @Override
    public void restSave(JsonDetail json) {
        Objects.requireNonNull(json.amount());
        Objects.requireNonNull(json.bought());
        Objects.requireNonNull(json.idList());
        Objects.requireNonNull(json.idItem());
        DetailList detailList = new DetailList();
        ShoppingList shoppingList = getShoppingListById(json.idList());
        detailList.setShoppingList(shoppingList);
        detailList.setBought(json.bought());
        detailList.setAmount(json.amount());
        Item item = getItemById(json.idItem());
        detailList.setItem(item);
        BigDecimal total = item.getPrice().multiply(BigDecimal.valueOf(json.amount().longValue()));
        detailList.setTotal(total);
        repository.save(detailList);
    }

    private Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no item with " + "the id " + id));
    }

    @Override
    public void restUpdate(JsonDetail json) {
        Objects.requireNonNull(json.id());
        Item item;
        DetailList detailList = repository.findById(json.id()).orElseThrow(() -> new ApplicationBusinessException("There is no detail with " + "the id " + json.id()));
        if (Objects.nonNull(json.idItem())) {
            item = getItemById(json.idItem());
            detailList.setItem(item);
        }
        if (Objects.nonNull(json.amount())) {
            detailList.setAmount(json.amount());
            item = getItemById(json.idItem());
            BigDecimal total = item.getPrice().multiply(BigDecimal.valueOf(json.amount().longValue()));
            detailList.setTotal(total);
        }
        if (Objects.nonNull(json.bought())) {
            detailList.setBought(json.bought());
        }
        if (Objects.nonNull(json.idList())) {
            ShoppingList list = getShoppingListById(json.idList());
            detailList.setShoppingList(list);
        }
        repository.save(detailList);
    }

    @Override
    public void restDelete(Long id) {
        Objects.requireNonNull(id);
        repository.deleteById(id);
    }


}