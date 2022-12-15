package com.lab.shoppinglist.views.shoppinglist;

import com.lab.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.lab.shoppinglist.api.exceptions.message.ErrorMessage;
import com.lab.shoppinglist.model.list.DetailList;
import com.lab.shoppinglist.services.list.ListService;
import com.lab.shoppinglist.views.ShowMessagesForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("view/list")
@Slf4j
@Scope(value = "session")
public class ListController {

    private ShowMessagesForm messagesForm;
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping("/register")
    public String getViewList(Model model ){
        if(messagesForm != null){
            model.addAttribute(messagesForm.getDescription(), true);
        }
        model.addAttribute("listDetailToAdd", listService.getItemDetailListToAdd());
        model.addAttribute("listDetailAdded", listService.getItemDetailListAdded());
        model.addAttribute("total", listService.getItemDetailListAdded().stream()
                .map(DetailList::getTotal).mapToDouble(BigDecimal::doubleValue).sum());
        return "list/shoppingList";
    }

    @PostMapping("/register")
    public String createList(@RequestParam("listName") String listName){
        try {
            listService.createListByName(listName);
            messagesForm = ShowMessagesForm.ADDED_ELEMENT;
        } catch (ApplicationBusinessException exception) {
            ErrorMessage errorMessage = exception.getErrorMessage();
            if(errorMessage.equals(ErrorMessage.DUPLICATED_LIST_NAME)) {
                messagesForm = ShowMessagesForm.DUPLICATED_ELEMENT;
            }
            if(errorMessage.equals(ErrorMessage.DETAIL_LIST_EMPTY)) {
                messagesForm = ShowMessagesForm.DETAIL_LIST_EMPTY;
            }
        }

        return "redirect:/view/list/register";
    }

    @PostMapping("/addDetail")
    public String addDetail(@RequestParam("amountDetail") String amountDetail,@RequestParam("itemIdAdd") String itemId){
        listService.addDetail(amountDetail,itemId);
        return "redirect:/view/list/register";
    }

    @PostMapping("/deleteDetail")
    public String deleteDetail(@RequestParam("itemIdDelete") String itemId){
        listService.deleteDetail(itemId);
        return "redirect:/view/list/register";
    }

}
