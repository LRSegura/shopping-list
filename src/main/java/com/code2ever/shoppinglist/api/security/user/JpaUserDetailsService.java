package com.code2ever.shoppinglist.api.security.user;

import com.code2ever.shoppinglist.model.user.UserApp;
import com.code2ever.shoppinglist.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> supplier = () -> new UsernameNotFoundException("Problem during authentication!");
        UserApp userApp = userRepository.getUserByUserName(username).orElseThrow(supplier);
        return new CustomUserDetails(userApp);
    }
}
