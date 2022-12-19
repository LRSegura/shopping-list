package com.lab.shoppinglist.views.shoppinglist;

import com.lab.shoppinglist.services.list.ListService;
import com.lab.shoppinglist.services.list.detail.DetailService;
import com.lab.shoppinglist.views.ShowMessagesForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("view/list")
@Slf4j
@Scope(value = "session")
public class ViewListController {

    private ShowMessagesForm messagesForm;
    private final ListService listService;

    private final DetailService detailService;


    public ViewListController(ListService listService, DetailService detailService) {
        this.listService = listService;
        this.detailService = detailService;
    }

    @GetMapping("/viewAllList")
    public String viewAllList(Model model){
        if(messagesForm != null){
            model.addAttribute(messagesForm.getDescription(), true);
        }
        model.addAttribute("shoppingLists", listService.getShoppingLists());
        model.addAttribute("totalLists", listService.getShoppingLists().size());
        return "list/viewList";
    }

    @PostMapping("/deleteList")
    public String deleteDetail(@RequestParam("listIdDelete") String listId){
        listService.deleteList(listId);
        messagesForm = ShowMessagesForm.DELETED_ELEMENT;
        return "redirect:/view/list/viewAllList";
    }

    @PostMapping("/viewDetailList")
    public String viewDetailList(@RequestParam("listIdView") String listId, Model model){
        model.addAttribute("detailListToBuy",listService.findDetailListByShoppingListToBuy(listId));
        model.addAttribute("detailListBought",listService.findDetailListByShoppingListBought(listId));
        model.addAttribute("totalToBuy",listService.getItemDetailListToBuy().size());
        model.addAttribute("totalBought",listService.getItemDetailListBought().size());
        model.addAttribute("listId",Long.parseLong(listId));
        return "list/viewDetailList";
    }




}