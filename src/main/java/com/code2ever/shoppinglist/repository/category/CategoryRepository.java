package com.code2ever.shoppinglist.repository.category;

import com.code2ever.shoppinglist.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsCategoryByDescription(String description);
    List<Category> findAllByOrderByRegisterDateDesc();
}