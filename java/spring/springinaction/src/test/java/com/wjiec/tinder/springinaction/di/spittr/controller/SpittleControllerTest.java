package com.wjiec.tinder.springinaction.di.spittr.controller;

import com.wjiec.tinder.springinaction.spittr.model.Spittle;
import com.wjiec.tinder.springinaction.spittr.repository.SpittleRepository;
import com.wjiec.tinder.springinaction.spittr.web.controller.SpittleController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class SpittleControllerTest {

    @Test
    void spittlesTest() throws Exception {
        List<Spittle> spittles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            spittles.add(Spittle.builder()
                .id(i)
                .content("Spittle-" + i)
                .createdAt(new Date())
                .latitude(Math.random())
                .longitude(Math.random())
                .build());
        }

        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito.when(mockRepository.findSpittles(20, 0)).thenReturn(spittles);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setSingleView(new InternalResourceView("/WEB-INF/view/spittles.jsp"))
            .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittles"))
            .andExpect(MockMvcResultMatchers.view().name("spittles"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
            .andExpect(MockMvcResultMatchers.model().attribute("spittleList",
                Matchers.hasItems(spittles.toArray())));
    }

    @Test
    void testSpittle() throws Exception {
        Spittle spittle = Spittle.builder().id(12345).content("Spittlr-12345").build();
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito.when(mockRepository.findOne(12345)).thenReturn(spittle);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp"))
            .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittle/12345"))
            .andExpect(MockMvcResultMatchers.view().name("spittle"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
            .andExpect(MockMvcResultMatchers.model().attribute("spittle", spittle));
    }

}
