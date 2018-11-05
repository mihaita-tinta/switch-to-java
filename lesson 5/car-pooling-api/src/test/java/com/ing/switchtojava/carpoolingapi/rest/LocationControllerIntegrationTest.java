package com.ing.switchtojava.carpoolingapi.rest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LocationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Sql("/location.sql")
    @Test
    @WithMockUser
    public void testFindAll() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/locations/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[" +
                                "{" +
                                "\"id\":1," +
                                "\"latitude\":11.11," +
                                "\"longitude\":12.12," +
                                "\"address\":\"loc1 \"," +
                                "\"city\":\"buc\"," +
                                "\"state\":\"B\"," +
                                "\"zip\":\"123\"" +
                                "}," +
                                "{" +
                                "\"id\":2," +
                                "\"latitude\":13.13," +
                                "\"longitude\":14.14," +
                                "\"address\":\"loc2 \"," +
                                "\"city\":\"buc\"," +
                                "\"state\":\"B\"," +
                                "\"zip\":\"124\"" +
                                "}" +
                                "]"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser
    public void testSaveNew() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/locations/")
                .content("{" +
                        "\"latitude\":11.99," +
                        "\"longitude\":12.12," +
                        "\"address\":\"loc1 \"," +
                        "\"city\":\"buc\"," +
                        "\"state\":\"B\"," +
                        "\"zip\":\"123\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{" +
                                "\"id\":1," +
                                "\"latitude\":11.99," +
                                "\"longitude\":12.12," +
                                "\"address\":\"loc1 \"," +
                                "\"city\":\"buc\"," +
                                "\"state\":\"B\"," +
                                "\"zip\":\"123\"" +
                                "}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Sql("/location.sql")
    @Test
    @WithMockUser
    public void testSaveExisting() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/locations/")
                .content("{" +
                        "\"id\":2," +
                        "\"latitude\":11.99," +
                        "\"longitude\":12.12," +
                        "\"address\":\"loc1 \"," +
                        "\"city\":\"buc\"," +
                        "\"state\":\"B\"," +
                        "\"zip\":\"123\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{" +
                                "\"id\":2," +
                                "\"latitude\":11.99," +
                                "\"longitude\":12.12," +
                                "\"address\":\"loc1 \"," +
                                "\"city\":\"buc\"," +
                                "\"state\":\"B\"," +
                                "\"zip\":\"123\"" +
                                "}"))
                .andDo(MockMvcResultHandlers.print());
    }
}
