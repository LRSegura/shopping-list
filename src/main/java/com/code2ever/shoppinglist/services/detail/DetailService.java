package com.code2ever.shoppinglist.services.detail;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.detail.JsonDetail;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.detail.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.repository.detail.DetailListRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class DetailService implements RestCrudOperations<JsonDetail> {
    private final DetailListRepository detailListRepository;
    private final ItemRepository itemRepository;
    private final ListRepository listRepository;

    public DetailService(DetailListRepository detailListRepository, ItemRepository itemRepository, ListRepository listRepository) {
        this.detailListRepository = detailListRepository;
        this.itemRepository = itemRepository;
        this.listRepository = listRepository;
    }
    @Override
    public List<? extends JsonData> restGet() {
        return detailListRepository.findAll().stream().map(detail -> new JsonDetail(detail.getId(), detail.getItem().getId(), detail.getAmount(), detail.getBought(), detail.getShoppingList().getId())).toList();
    }

    public List<? extends JsonData> restGet(Long idList) {
        ShoppingList shoppingList = getShoppingListById(idList);
        return detailListRepository.findDetailListByShoppingList(shoppingList).stream().map(detail -> new JsonDetail(detail.getId(), detail.getItem().getId(), detail.getAmount(), detail.getBought(), detail.getShoppingList().getId())).toList();
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
        detailListRepository.save(detailList);
    }

    private Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no item with " + "the id " + id));
    }

    @Override
    public void restUpdate(JsonDetail json) {
        Objects.requireNonNull(json.id());
        Item item;
        DetailList detailList = detailListRepository.findById(json.id()).orElseThrow(() -> new ApplicationBusinessException("There is no detail with " + "the id " + json.id()));
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
        detailListRepository.save(detailList);
    }

    @Override
    public void restDelete(Long id) {
        Objects.requireNonNull(id);
        detailListRepository.deleteById(id);
    }


}