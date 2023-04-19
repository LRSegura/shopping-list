package com.code2ever.shoppinglist.services.list;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.list.JsonList;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.api.util.UtilClass;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.list.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.repository.detail.DetailListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ListService implements RestCrudOperations<JsonList> {

    private final ListRepository listRepository;

    private final ItemRepository itemRepository;

    private final DetailListRepository detailListRepository;

    private final List<DetailList> itemDetailListToAdd;

    private final List<DetailList> itemDetailListAdded;

    public ListService(ListRepository listRepository, ItemRepository itemRepository, DetailListRepository detailListRepository) {
        this.listRepository = listRepository;
        this.itemRepository = itemRepository;
        this.detailListRepository = detailListRepository;
        itemDetailListAdded = new ArrayList<>();
        itemDetailListToAdd = getNewListDetail();
    }

    @Transactional
    public void createListByName(String listName) {
        if (itemDetailListAdded.isEmpty()) {
            throw new ApplicationBusinessException("");
        }

        if (listName.isEmpty() || listName.isBlank()) {
            listName = LocalDateTime.now().toString();
        }

        if (listRepository.existsByName(listName)) {
            throw new ApplicationBusinessException("");
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

    private ShoppingList getShoppingListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Error removing list"));
    }

    @Override
    public List<? extends JsonData> restGet() {
        return listRepository.findAll().stream().map(list -> new JsonList(list.getId(), list.getName(), list.getTotal())).toList();
    }

    @Override
    public void restSave(JsonList json) {
        Objects.requireNonNull(json.name());
        UtilClass.requireNonBlankString(json.name(), "The name is empty");
        if (isDuplicatedList(json.name())) {
            throw new ApplicationBusinessException("List name duplicated");
        }
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(json.name());
        shoppingList.setTotal(BigDecimal.ZERO);
        listRepository.save(shoppingList);
    }

    public boolean isDuplicatedList(String name) {
        return listRepository.existsByName(name);
    }

    @Override
    public void restUpdate(JsonList jsonRequest) {
        Objects.requireNonNull(jsonRequest.id());
        Long id = jsonRequest.id();
        ShoppingList list = listRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "List not found with id" + id;
            return new ApplicationBusinessException(errorMessage);
        });
        if (Objects.nonNull(jsonRequest.name())) {
            list.setName(jsonRequest.name());
        }
        listRepository.save(list);
    }

    @Override
    public void restDelete(Long id) {
        Objects.requireNonNull(id);
        listRepository.deleteById(id);
    }
}