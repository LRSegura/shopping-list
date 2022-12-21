package com.lab.shoppinglist.views.user;


import com.lab.shoppinglist.services.user.UserApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("view")
@Slf4j
@Scope(value = "session")
public class SignUpController {

    private UserApplicationService userApplicationService;

    public SignUpController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute SignupForm form){
//        Map<String, Integer> map = userApplicationService.getGenderMap();
//        model.addAttribute("genderMap",map);
        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute SignupForm form){
        log.info(form.toString());
        userApplicationService.saveUser(form);
        return "redirect:/login";
    }
}