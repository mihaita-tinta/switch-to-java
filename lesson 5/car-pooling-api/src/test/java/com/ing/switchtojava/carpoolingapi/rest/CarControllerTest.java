package com.ing.switchtojava.carpoolingapi.rest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Sql("/car.sql")
    @Test
    @WithMockUser
    public void testFindAll() throws Exception {
        // TODO 1 list all cars.

        mvc.perform(MockMvcRequestBuilders.get("/cars/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[{\"id\":1,\"number\":\"IL11ABC\",\"seats\":2}]"))
                .andDo(MockMvcResultHandlers.print());

    }
}
