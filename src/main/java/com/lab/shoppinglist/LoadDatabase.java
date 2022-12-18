package com.lab.shoppinglist;

import com.lab.shoppinglist.repository.item.category.ItemCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ItemCategoryRepository repository){
        return args -> {
//            ItemCategory item1 = new ItemCategory();
//            ItemCategory item2 = new ItemCategory();
//            item1.setDescription("Item1");
//            item2.setDescription("Item2");
//            repository.save(item1);
//            repository.save(item2);
        };
    }
}