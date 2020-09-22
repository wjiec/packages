package com.wjiec.tinder.springinaction.di.spittr.controller;

import com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO;
import com.wjiec.tinder.springinaction.spittr.model.Spitter;
import com.wjiec.tinder.springinaction.spittr.repository.SpitterRepository;
import com.wjiec.tinder.springinaction.spittr.web.controller.SpitterController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

class SpitterControllerTest {

    @Test
    void registerFormTest() throws Exception {
        SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
            .andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }

    @Test
    void processRegistration() throws Exception {
        SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
        SpitterDTO unsaved = SpitterDTO.builder().username("jayson").password("password").build();
        Spitter saved = Spitter.builder().id(0).username("jayson").password("password").createdAt(new Date()).build();
        Mockito.when(mockRepository.save(unsaved)).thenReturn(saved);

        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/spitter/register")
            .param("username", "jayson")
            .param("password", "password");

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/jayson"));

        Mockito.verify(mockRepository, Mockito.atLeastOnce()).save(unsaved);
    }

}
