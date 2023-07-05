package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.model.category.Category;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebServiceCategoryTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository repository;

    @Test
    @Order(1)
    @WithMockUser
    void getCategories() throws Exception {
        mvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    @WithMockUser
    void saveCategory() throws Exception {
        String json = "{\"description\":\"Test1\"}";
        mvc.perform(post("/api/category").with(csrf()).content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    @Order(3)
    @WithMockUser
    void updateCategory() throws Exception {
        Category category =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Categories does not exits"));
        String json = String.format("{\"id\":%d,\"description\":\"Test2\"}", category.getId());
        mvc.perform(put("/api/category").with(csrf()).content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(4)
    @WithMockUser
    void deleteCategory() throws Exception {
        Category category =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Categories does not exits"));
        Long id = category.getId();
        mvc.perform(delete("/api/category").queryParam("id", id.toString()).with(csrf())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
