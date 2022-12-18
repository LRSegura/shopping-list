package com.lab.shoppinglist.services.list;

import com.lab.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.lab.shoppinglist.api.exceptions.message.ErrorMessage;
import com.lab.shoppinglist.model.item.Item;
import com.lab.shoppinglist.model.list.DetailList;
import com.lab.shoppinglist.model.list.ShoppingList;
import com.lab.shoppinglist.repository.DetailListRepository;
import com.lab.shoppinglist.repository.ItemRepository;
import com.lab.shoppinglist.repository.ListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ListService {

    private final ListRepository listRepository;

    private final ItemRepository itemRepository;

    private final DetailListRepository detailListRepository;

    private final List<DetailList> itemDetailListToAdd;

    private final List<DetailList> itemDetailListAdded;

    private final List<DetailList> itemDetailListToBuy;

    private final List<DetailList> itemDetailListBought;

    public ListService(ListRepository listRepository, ItemRepository itemRepository, DetailListRepository detailListRepository) {
        this.listRepository = listRepository;
        this.itemRepository = itemRepository;
        this.detailListRepository = detailListRepository;
        itemDetailListAdded = new ArrayList<>();
        itemDetailListToAdd = getNewListDetail();
        itemDetailListToBuy = new ArrayList<>();
        itemDetailListBought = new ArrayList<>();
    }

    @Transactional
    public void createListByName(String listName) {
        if (itemDetailListAdded.isEmpty()) {
            throw new ApplicationBusinessException(ErrorMessage.DETAIL_LIST_EMPTY);
        }

        if (listName.isEmpty() || listName.isBlank()) {
            listName = LocalDateTime.now().toString();
        }

        if (listRepository.existsByName(listName)) {
            throw new ApplicationBusinessException(ErrorMessage.DUPLICATED_LIST_NAME);
        }

        BigDecimal total = BigDecimal.valueOf(itemDetailListAdded.stream().map(DetailList::getTotal).mapToDouble(BigDecimal::doubleValue).sum());
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(listName);
        shoppingList.setTotal(total);

        listRepository.save(shoppingList);
        itemDetailListAdded.forEach(detailList -> {
            detailList.setShoppingList(shoppingList);
            detailListRepository.save(detailList);
        });

        itemDetailListAdded.clear();
        itemDetailListToAdd.clear();
        itemDetailListToAdd.addAll(getNewListDetail());
    }

    private List<DetailList> getNewListDetail() {
        return itemRepository.findAll().stream().map(this::createDetailListFromItem).collect(Collectors.toList());
    }

    private DetailList createDetailListFromItem(Item item) {
        DetailList detailList = new DetailList();
        detailList.setItem(item);
        detailList.setAmount(1);
        return detailList;
    }

    public void addDetail(String amountDetail, String itemId) {
        Integer amount = Integer.valueOf(amountDetail);
        Long id = Long.parseLong(itemId);
        Predicate<DetailList> filter = detailList -> detailList.getItem().getId().equals(id);
        itemDetailListToAdd.stream().filter(filter).findFirst().ifPresent(detailList -> {
            BigDecimal price = detailList.getItem().getPrice();
            detailList.setAmount(amount);
            detailList.setBought(false);
            detailList.setTotal(BigDecimal.valueOf(amount.longValue()).multiply(price));
            itemDetailListAdded.add(detailList);
        });
        itemDetailListToAdd.removeIf(filter);
    }

    public void deleteDetail(String itemId) {
        Long id = Long.parseLong(itemId);
        Predicate<DetailList> filter = detailList -> detailList.getItem().getId().equals(id);
        itemDetailListAdded.stream().filter(filter).findFirst().ifPresent(itemDetailListToAdd::add);
        itemDetailListAdded.removeIf(filter);
    }

    @Transactional
    public void deleteList(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        List<DetailList> detailLists = detailListRepository.findDetailListByShoppingList(shoppingList);
        detailListRepository.deleteAll(detailLists);
        listRepository.delete(shoppingList);
    }

    public List<DetailList> getDetailListByList(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        return detailListRepository.findDetailListByShoppingList(shoppingList);
    }

    private ShoppingList getShoppingListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Error removing list"));
    }

    public List<DetailList> findDetailListByShoppingListToBuy(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        itemDetailListToBuy.clear();
        itemDetailListToBuy.addAll(detailListRepository.findDetailListByShoppingListAndBought(shoppingList, false));
        return itemDetailListToBuy;
    }

    public List<DetailList> findDetailListByShoppingListBought(String listId) {
        ShoppingList shoppingList = getShoppingListById(Long.parseLong(listId));
        itemDetailListBought.clear();
        itemDetailListBought.addAll(detailListRepository.findDetailListByShoppingListAndBought(shoppingList, true));
        return itemDetailListBought;
    }

    public List<DetailList> getItemDetailListToAdd() {
        return itemDetailListToAdd;
    }

    public List<DetailList> getItemDetailListAdded() {
        return itemDetailListAdded;
    }

    public List<DetailList> getItemDetailListToBuy() {
        return itemDetailListToBuy;
    }

    public List<DetailList> getItemDetailListBought() {
        return itemDetailListBought;
    }

    public List<ShoppingList> getShoppingLists() {
        return listRepository.findAll();
    }
}