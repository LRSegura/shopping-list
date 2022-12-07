package com.lab.shoppinglist.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/helloworld")
public class helloworldController {

    @GetMapping("/helloworld")
    public String getView(){
        return "helloworld";
    }
}
