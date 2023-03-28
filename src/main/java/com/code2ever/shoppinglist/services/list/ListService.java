package com.code2ever.shoppinglist.services.list;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.exceptions.message.ErrorMessage;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.list.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.repository.list.detail.DetailListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ListService extends AService{

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

    private ShoppingList getShoppingListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Error removing list"));
    }

    @Override
    protected ListRepository getListRepository() {
        return listRepository;
    }

    @Override
    protected DetailListRepository getDetailListRepository() {
        return detailListRepository;
    }

    public List<DetailList> getItemDetailListToAdd() {
        return itemDetailListToAdd;
    }

    public List<DetailList> getItemDetailListAdded() {
        return itemDetailListAdded;
    }

    public List<ShoppingList> getShoppingLists() {
        return listRepository.findAll();
    }
}