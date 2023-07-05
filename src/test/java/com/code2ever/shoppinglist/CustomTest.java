package com.code2ever.shoppinglist;

import com.code2ever.shoppinglist.model.category.Category;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CustomTest {

    @Bean
    Category getCategory(){
        Category category = new Category();
        category.setDescription("hello");
        return category;
    }
}
