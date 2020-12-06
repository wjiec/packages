package com.wjiec.barn.api.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TipsControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    TipsControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void random() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tips/random"))
            .andExpect(status().isOk());
    }

}
