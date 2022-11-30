package com.lab.shoppinglist.views;

import com.lab.shoppinglist.services.ItemCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("view/item/category")
@Slf4j
@Scope(value = "session")
public class ItemCategoryController {

    private ShowMessagesForm messagesForm;

    private final ItemCategoryService service;

    public ItemCategoryController(ItemCategoryService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }

    @GetMapping("/register")
    public String getItemCategoryView(Model model, @ModelAttribute ItemCategoryForm itemCategoryForm){
        if(messagesForm != null){
            model.addAttribute(messagesForm.getDescription(), true);
        }
        model.addAttribute("allItemCategory", service.getItemCategories());
        return "item/category/itemcategory";
    }

    @PostMapping("/register")
    public String postItemCategory(@ModelAttribute ItemCategoryForm itemCategoryForm, Model model){
        if(service.isItemCategoryDuplicated(itemCategoryForm.getCategoryName())){
            messagesForm = ShowMessagesForm.DUPLICATED_ELEMENT;
            return "redirect:/view/item/category/register";
        }
        service.save(itemCategoryForm.getCategoryName());
        messagesForm = ShowMessagesForm.ADDED_ELEMENT;
        return "redirect:/view/item/category/register";
    }

    @PostMapping("/delete")
    public String deleteItemCategory(@RequestParam("categoryId") String categoryId, Model model){
        service.delete(Long.parseLong(categoryId));
        messagesForm = ShowMessagesForm.DELETED_ELEMENT;
        return "redirect:/view/item/category/register";
    }


    @PostMapping("/hello")
    public String postRequest(@RequestParam("textTest") String text, Model model){
        model.addAttribute("value", text);
        return "helloworld";
    }

}
