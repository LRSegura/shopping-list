package com.code2ever.shoppinglist.services.user;

import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl {
//
//    private UserRepository userRepository;
//    public UserDetailServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.getUserByUserName(username).orElseThrow(() -> new IllegalArgumentException());
//        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(authority);
//        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.
//                User(user.getUserName(), user.getPassword(), authorities);
//        return userDetails;
//    }
}