package com.code2ever.shoppinglist;

//import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc
class ShoppingListApplicationTests {

//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    @WithMockUser
//    void contextLoads() throws Exception {
//        mvc.perform(get("/api/category"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    @WithMockUser
//    void saveTest() throws Exception {
//        JsonCategory jsonCategory = new JsonCategory(null, "Test3");
//        mvc.perform(post("/api/category").with(csrf())
//                .content(new ObjectMapper().writeValueAsString(jsonCategory))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(jsonPath("$.description", is("Test3")));
//    }
//
//    @Test
//    @WithMockUser
//    void save() throws Exception {
//        JsonCategory jsonCategory = new JsonCategory(null, "Test3");
//        mvc.perform(post("/api/category").with(csrf())
//                        .content(new ObjectMapper().writeValueAsString(jsonCategory))
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated());

//    }

}
