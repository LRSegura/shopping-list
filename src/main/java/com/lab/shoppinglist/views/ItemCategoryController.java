package com.lab.shoppinglist.views;

import com.lab.shoppinglist.services.ItemCategoryService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("view/item/category")
//@Scope(value = "request")
public class ItemCategoryController {

    private final ItemCategoryService service;

    public ItemCategoryController(ItemCategoryService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }

    @GetMapping("/register")
    public String getItemCategoryView(Model model){
        model.addAttribute("allItemCategory", service.getItemCategories());
        return "item/category/itemcategory";
    }

    @PostMapping("/register")
    public String postItemCategory(@RequestParam("category") String category, Model model){
        if(service.isItemCategoryDuplicated(category)){
            model.addAttribute("itemCategoryDuplicated", true);
            return "item/category/itemcategory";
        }
        service.save(category);
        model.addAttribute("allItemCategory", service.getItemCategories());
        model.addAttribute("itemCategorySaved", true);
        return "item/category/itemcategory";
    }

    @PostMapping("/delete")
    public String deleteItemCategory(@RequestParam("categoryId") String categoryId, Model model){
        service.delete(Long.parseLong(categoryId));
        model.addAttribute("itemCategoryDeleted", true);
        return "redirect:/view/item/category/register";
    }


    @PostMapping("/hello")
    public String postRequest(@RequestParam("textTest") String text, Model model){
        model.addAttribute("value", text);
        return "helloworld";
    }

}
