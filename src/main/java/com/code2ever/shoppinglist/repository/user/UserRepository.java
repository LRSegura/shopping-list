package com.code2ever.shoppinglist.repository.user;

import com.code2ever.shoppinglist.model.user.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> getUserByUserName(String userName);
}