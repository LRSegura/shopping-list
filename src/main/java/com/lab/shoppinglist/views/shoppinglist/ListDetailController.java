package com.lab.shoppinglist.views.shoppinglist;

import com.lab.shoppinglist.repository.DetailListRepository;
import com.lab.shoppinglist.repository.ListRepository;
import com.lab.shoppinglist.services.list.detail.DetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("view/detailList")
@Slf4j
@Scope(value = "session")
public class ListDetailController {

    private final DetailService detailService;



    public ListDetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/viewDetailList")
    public String viewDetailList(Model model){
        model.addAttribute("detailListToBuy",detailService.getItemDetailListToBuy());
        model.addAttribute("detailListBought",detailService.getItemDetailListBought());
        model.addAttribute("totalToBuy",detailService.getItemDetailListToBuy().size());
        model.addAttribute("totalBought",detailService.getItemDetailListBought().size());
        return "list/viewDetailList";
    }

    @PostMapping("/buyDetailList")
    public String buyDetailList(@RequestParam("detailIdBuy") String detailIdBuy, @RequestParam("listIdBuy") String listIdBuy){
        detailService.findDetailListByShoppingListToBuy(listIdBuy);
        detailService.findDetailListByShoppingListBought(listIdBuy);
        detailService.buyItem(detailIdBuy);
        return "redirect:/view/detailList/viewDetailList";
    }

    @PostMapping("/cancelDetailList")
    public String cancelDetailList(@RequestParam("detailIdCancel") String detailIdCancel, @RequestParam("listIdCancel") String listIdCancel){
        detailService.findDetailListByShoppingListToBuy(listIdCancel);
        detailService.findDetailListByShoppingListBought(listIdCancel);
        detailService.cancel(detailIdCancel);
        return "redirect:/view/detailList/viewDetailList";
    }

}