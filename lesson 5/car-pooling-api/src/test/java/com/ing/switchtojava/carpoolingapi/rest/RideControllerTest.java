package com.ing.switchtojava.carpoolingapi.rest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode =DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "user", password = "password", roles = "USER")
public class RideControllerTest {

    @Autowired
    private MockMvc mvc;

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql"})
    @Test
    public void testFindAll() throws Exception {
        // TODO 3 list all rides.

        mvc.perform(MockMvcRequestBuilders.get("/rides/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[]"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void testFindOne() throws Exception {
        // TODO 3 list one ride.

        mvc.perform(MockMvcRequestBuilders.get("/rides/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testSaveOne() throws Exception {
        // TODO 3 save ride
    }

    @Test
    public void testFindBy() throws Exception {
        // TODO 3 find by date
        mvc.perform(MockMvcRequestBuilders.get("/rides/")
                .param("from", "Crangasi")
                .param("to", "Arcul de Trimuf")
                .param("before", ZonedDateTime.now()
                        .plusDays(1)
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .param("after", ZonedDateTime.now()
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{}"))
                .andDo(MockMvcResultHandlers.print());
    }
}
