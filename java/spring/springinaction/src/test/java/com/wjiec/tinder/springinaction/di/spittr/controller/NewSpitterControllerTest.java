package com.wjiec.tinder.springinaction.di.spittr.controller;

import com.wjiec.tinder.springinaction.spittr.SpittrWebAppInitializer;
import com.wjiec.tinder.springinaction.spittr.web.controller.SpitterController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpittrWebAppInitializer.class)
@WebMvcTest(controllers = SpitterController.class)
class NewSpitterControllerTest {

    private MockMvc mockMvc;

    @Autowired
    NewSpitterControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void spitterDTOInvalidValidationReject() throws Exception {
        mockMvc.perform(post("/spitter/register").param("username", "-").param("password", "-"))
            .andExpect(view().name("registerForm"));
    }

}
