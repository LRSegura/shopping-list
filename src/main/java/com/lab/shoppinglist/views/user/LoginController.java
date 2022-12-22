package com.lab.shoppinglist.views.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("view")
@Slf4j
@Scope(value = "session")
public class LoginController {

    @GetMapping("/login")
    public String getLogin(){
        return "login/login";
    }
    @PostMapping("/login")
    public String postLogin(){
        return "redirect:/view/list/viewAllList";
    }
}