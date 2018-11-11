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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RideControllerTest {

    @Autowired
    private MockMvc mvc;

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void testFindAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rides/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[{\"id\":1," +
                                                "\"from\":{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"loc1 \",\"city\":\"buc\",\"state\":\"B\",\"zip\":\"123\"}," +
                                                "\"to\":{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"loc2 \",\"city\":\"buc\",\"state\":\"B\",\"zip\":\"124\"}," +
                                                "\"when\":\"1970-01-01T13:25:45.194+02:00\"," +
                                                "\"car\":{\"id\":1,\"number\":\"IL11ABC\",\"seats\":3}," +
                                                "\"status\":\"PENDING\"," +
                                                "\"passengers\":[]," +
                                                "\"requests\":[{\"id\":1,\"passenger\":{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"},\"status\":\"ACCEPTED\"}," +
                                                "{\"id\":2,\"passenger\":{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"},\"status\":\"ACCEPTED\"}," +
                                                "{\"id\":3,\"passenger\":{\"id\":3,\"firstName\":\"Ioana\",\"lastName\":\"Z\"},\"status\":\"ACCEPTED\"}]}]"))
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
                        .string("{\"id\":1,\"from\":{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"loc1 \",\"city\":\"buc\",\"state\":\"B\",\"zip\":\"123\"},\"to\":{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"loc2 \",\"city\":\"buc\",\"state\":\"B\",\"zip\":\"124\"},\"when\":\"1970-01-01T13:37:04.206+02:00\",\"car\":{\"id\":1,\"number\":\"IL11ABC\",\"seats\":3},\"status\":\"PENDING\",\"passengers\":[],\"requests\":[{\"id\":1,\"passenger\":{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"},\"status\":\"ACCEPTED\"},{\"id\":2,\"passenger\":{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"},\"status\":\"ACCEPTED\"},{\"id\":3,\"passenger\":{\"id\":3,\"firstName\":\"Ioana\",\"lastName\":\"Z\"},\"status\":\"ACCEPTED\"}]}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void testSaveOne() throws Exception {
        // TODO 3 save ride
    }
    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
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
