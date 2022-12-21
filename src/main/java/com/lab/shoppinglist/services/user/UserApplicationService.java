package com.lab.shoppinglist.services.user;

import com.lab.shoppinglist.model.user.User;
import com.lab.shoppinglist.repository.user.UserRepository;
import com.lab.shoppinglist.views.user.SignupForm;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(SignupForm form){
        User user = new User();
        user.setUserName(form.getUserName());
        user.setPassword(form.getPassword());
        userRepository.save(user);
    }

    //    public Map<String,Integer> getGenderMap(){
//        Map<String, Integer> genderMap = new LinkedHashMap<>();
//        genderMap.put("male", 1);
//        genderMap.put("female", 2);
//        return genderMap;
//    }
}