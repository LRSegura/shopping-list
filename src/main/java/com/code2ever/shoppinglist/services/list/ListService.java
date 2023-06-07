package com.code2ever.shoppinglist.services.list;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.list.JsonList;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.util.UtilClass;
import com.code2ever.shoppinglist.model.detail.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.detail.DetailListRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ListService implements CrudRestOperations<JsonList> {

    private final ListRepository listRepository;
    private final DetailListRepository detailListRepository;

    public ListService(ListRepository listRepository, DetailListRepository detailListRepository) {
        this.listRepository = listRepository;
        this.detailListRepository = detailListRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return listRepository.findAll().stream().map(list -> new JsonList(list.getId(), list.getName(),
                list.getTotalPrice(), list.getTotalItems())).toList();
    }

    @Override
    public void restSave(JsonList json) {
        Objects.requireNonNull(json.name(),"Name cant be null");
        UtilClass.requireNonBlankString(json.name(), "The name is empty");
        if (isDuplicatedList(json.name())) {
            throw new ApplicationBusinessException("List name duplicated");
        }
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(json.name());
        shoppingList.setTotalPrice(BigDecimal.ZERO);
        shoppingList.setTotalItems(0);
        listRepository.save(shoppingList);
    }

    public boolean isDuplicatedList(String name) {
        return listRepository.existsByName(name);
    }

    @Override
    public void restUpdate(JsonList jsonRequest) {
        Objects.requireNonNull(jsonRequest.id(),"Id cant be null");
        Long id = jsonRequest.id();
        ShoppingList list = getShoppingList(id);
        if (Objects.nonNull(jsonRequest.name())) {
            list.setName(jsonRequest.name());
        }
        listRepository.save(list);
    }

    @Override
    public void restDelete(Long id) {
        Objects.requireNonNull(id,"Id cant be null");
        ShoppingList list = getShoppingList(id);
        List<DetailList> detailList = detailListRepository.findDetailListByShoppingList(list);
        detailListRepository.deleteAll(detailList);
        listRepository.deleteById(id);
    }

    private ShoppingList getShoppingList(Long id){
        return listRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "List not found with id" + id;
            return new ApplicationBusinessException(errorMessage);
        });
    }
}