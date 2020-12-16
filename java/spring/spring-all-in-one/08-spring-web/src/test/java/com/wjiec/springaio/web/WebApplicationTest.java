package com.wjiec.springaio.web;

import com.wjiec.springaio.web.controller.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ContextConfiguration(classes = WebApplicationInitializer.class)
class WebApplicationTest {

    @Test
    void contextLoaded() {}

    @Test
    void testHomePage() throws Exception {
        IndexController controller = new IndexController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(view().name("home"));
    }

}
