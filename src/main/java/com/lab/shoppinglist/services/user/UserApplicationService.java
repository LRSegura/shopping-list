package com.lab.shoppinglist.services.user;

import com.lab.shoppinglist.model.user.User;
import com.lab.shoppinglist.repository.user.UserRepository;
import com.lab.shoppinglist.views.user.SignupForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private UserRepository userRepository;

    private PasswordEncoder encoder;

    public UserApplicationService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void saveUser(SignupForm form){
        User user = new User();
        user.setUserName(form.getUserName());
        user.setPassword(encoder.encode(form.getPassword()));
        userRepository.save(user);
    }
}