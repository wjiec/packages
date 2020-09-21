package com.wjiec.tinder.springinaction.di.spittr.controller;

import com.wjiec.tinder.springinaction.spittr.web.controller.SpitterController;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class SpitterControllerTest {

    @Test
    void registerFormTest() throws Exception {
        SpitterController controller = new SpitterController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
            .andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }

}
