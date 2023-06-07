package com.code2ever.shoppinglist.services.detail;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.detail.JsonAddedDetail;
import com.code2ever.shoppinglist.api.rest.detail.JsonDetail;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.detail.DetailList;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import com.code2ever.shoppinglist.repository.detail.DetailListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DetailService implements CrudRestOperations<JsonDetail> {
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
        return detailListRepository.findDetailListByShoppingList(shoppingList).stream().map(detail -> new JsonAddedDetail(detail.getId(), detail.getItem().getName(), detail.getTotal(), detail.getAmount(), detail.getBought())).toList();
    }

    public List<? extends JsonData> getDetailToBuy(Long idList) {
        return getDetail(idList,false);
    }

    public List<? extends JsonData> getDetailBought(Long idList) {
        return getDetail(idList,true);
    }

    private List<? extends JsonData> getDetail(Long idList, Boolean bought) {
        ShoppingList shoppingList = getShoppingListById(idList);
        return detailListRepository.findDetailListByShoppingListAndBought(shoppingList, bought)
                .stream().map(detail -> new JsonAddedDetail(detail.getId(), detail.getItem().getName(), detail.getTotal(), detail.getAmount(), detail.getBought())).toList();
    }

    private ShoppingList getShoppingListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no list with " + "the id " + id));
    }

    public void buyDetail(Long idDetail){
        checkDetailAsBought(idDetail,true);
    }

    public void cancelBuyDetail(Long idDetail){
        checkDetailAsBought(idDetail,false);
    }

    private void checkDetailAsBought(Long idDetail, Boolean isCheck){
        DetailList detailList = getDetailById(idDetail);
        detailList.setBought(isCheck);
        detailListRepository.save(detailList);
    }

    private DetailList getDetailById(Long id){
        return detailListRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no Detail " +
                "with" +
                "the id " + id));
    }

    @Override
    @Transactional
    public void restSave(JsonDetail json) {
        Objects.requireNonNull(json.amount(), "Amount cant be null");
        Objects.requireNonNull(json.bought(), "Bought cant be null");
        Objects.requireNonNull(json.idList(), "IdList cant be null");
        Objects.requireNonNull(json.idItem(), "IdItem cant be null");
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
        BigDecimal totalPriceList = detailListRepository.getTotalPriceByShoppingList(shoppingList);
        shoppingList.setTotalPrice(totalPriceList);
        shoppingList.setTotalItems(detailListRepository.countAllByShoppingList(shoppingList));
    }

    private Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no item with " + "the id " + id));
    }

    @Override
    public void restUpdate(JsonDetail json) {
        Objects.requireNonNull(json.id(), "Id cant be null");
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
    @Transactional
    public void restDelete(Long id) {
        Objects.requireNonNull(id, "Id cant be null");
        DetailList detailList = detailListRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is " + "no detail " + "with the id " + id));
        ShoppingList shoppingList = detailList.getShoppingList();
        detailListRepository.deleteById(id);
        BigDecimal totalPriceList = detailListRepository.getTotalPriceByShoppingList(shoppingList);
        shoppingList.setTotalPrice(totalPriceList);
        shoppingList.setTotalItems(detailListRepository.countAllByShoppingList(shoppingList));

    }
}