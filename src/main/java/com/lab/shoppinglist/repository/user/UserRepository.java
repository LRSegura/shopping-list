package com.lab.shoppinglist.repository.user;

import com.lab.shoppinglist.model.user.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> getUserByUserName(String userName);
}