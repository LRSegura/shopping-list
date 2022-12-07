package com.lab.shoppinglist.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserListController {
    @GetMapping("/list")
    public String getList(){
        return "layout/list";
    }
}
