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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RideControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    @Sql("/ride.sql")
    public void testFindAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rides/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[" +
                                                "{\"id\":1,\"" +
                                                "from\":" + "{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"Crangasi \",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"123\"},\"" +
                                                "to\":{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"Arcul de Trimuf\",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"124\"},\"" +
                                                "when\":\"2019-11-01T23:29:46.123+02:00\",\"" +
                                                "car\":{\"id\":1,\"number\":\"IL11ABC\",\"seats\":2},\"status\":\"PENDING\",\"" +
                                                "passengers\":[" +
                                                    "{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                                    "{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"}" +
                                                "]" +
                                                "}]"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser
    @Sql("/ride.sql")
    public void testFindOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rides/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"" +
                                "from\":" + "{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"Crangasi \",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"123\"},\"" +
                                "to\":{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"Arcul de Trimuf\",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"124\"},\"" +
                                "when\":\"2019-11-01T23:29:46.123+02:00\",\"" +
                                "car\":{\"id\":1,\"number\":\"IL11ABC\",\"seats\":2},\"status\":\"PENDING\",\"" +
                                "passengers\":[" +
                                "{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                "{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"}" +
                                "]" +
                                "}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser
    public void testSaveOne() throws Exception {
        // TODO 3 save ride
        mvc.perform(MockMvcRequestBuilders.put("/rides/")
                .content("{" +
                        "from\":" + "{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"Crangasi \",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"123\"},\"" +
                        "to\":{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"Arcul de Trimuf\",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"124\"},\"" +
                        "when\":\"2019-11-01T23:29:46.123+02:00\",\"" +
                        "car\":{\"id\":1,\"number\":\"IL11ABC\",\"seats\":2},\"status\":\"PENDING\",\"" +
                        "passengers\":[]" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .string("{\"id\":1,\"" +
                        "from\":" + "{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"Crangasi \",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"123\"},\"" +
                        "to\":{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"Arcul de Trimuf\",\"city\":\"Bucuresti\",\"state\":\"B\",\"zip\":\"124\"},\"" +
                        "when\":\"2019-11-01T23:29:46.123+02:00\",\"" +
                        "car\":{\"id\":1,\"number\":\"IL11ABC\",\"seats\":2},\"status\":\"PENDING\",\"" +
                        "passengers\":[]" +
                        "}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser
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
