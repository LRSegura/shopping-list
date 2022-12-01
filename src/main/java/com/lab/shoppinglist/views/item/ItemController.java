package com.lab.shoppinglist.views.item;

import com.lab.shoppinglist.services.item.ItemService;
import com.lab.shoppinglist.views.ShowMessagesForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("view/item")
@Slf4j
@Scope(value = "session")
public class ItemController {

    private ShowMessagesForm messagesForm;
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/register")
    public String getItemView(@ModelAttribute ItemForm itemForm, Model model ){
        if(messagesForm != null){
            model.addAttribute(messagesForm.getDescription(), true);
        }
        model.addAttribute("categories", itemService.getItemCategoryList());
        model.addAttribute("items", itemService.getAllItems());
        return "item/item";
    }

    @PostMapping ("/register")
    public String postItemView(@ModelAttribute ItemForm itemForm ){
        if(itemService.isItemDuplicated(itemForm.getName())){
            messagesForm = ShowMessagesForm.DUPLICATED_ELEMENT;
            return "redirect:/view/item/register";
        }
        itemService.save(itemForm);
        messagesForm = ShowMessagesForm.ADDED_ELEMENT;
        return "redirect:/view/item/register";
    }

    @PostMapping ("/delete")
    public String deleteItemView(@RequestParam("itemId") String id){
        itemService.delete(Long.parseLong(id));
        messagesForm = ShowMessagesForm.DELETED_ELEMENT;
        return "redirect:/view/item/register";
    }

}
