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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode =DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "user", password = "password", roles = "USER")
public class PassengerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testFindAll() throws Exception {
        // TODO 2 list all passengers.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Sql("/passenger.sql")
    @Test
    public void testFindOne() throws Exception {
        // TODO 2 list one passenger.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSavePassenger() throws Exception {
        // TODO 2 save passenger

        mvc.perform(MockMvcRequestBuilders.put("/passengers/")
                .content("{" +
                        "\"firstName\":\"Marcela\"," +
                        "\"lastName\":\"Popescu\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{" +
                                "\"id\":1," +
                                "\"firstName\":\"Marcela\"," +
                                "\"lastName\":\"Popescu\"" +
                                "}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Sql({"/passenger.sql", "/location.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void testListRequestsByPassenger() throws Exception {
        // TODO 2 requests for a passenger.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/1/ride-requests/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{" +
                                                    "\"id\":1," +
                                                    "\"passenger\":{" +
                                                        "\"id\":1," +
                                                        "\"firstName\":\"Ana\"," +
                                                        "\"lastName\":\"Z\"" +
                                                    "}," +
                                                    "\"ride\":{" +
                                                        "\"id\":1," +
                                                        "\"from\":{" +
                                                            "\"id\":1," +
                                                            "\"latitude\":11.11," +
                                                            "\"longitude\":12.12," +
                                                            "\"address\":\"loc1 \"," +
                                                            "\"city\":\"buc\"," +
                                                            "\"state\":\"B\"," +
                                                            "\"zip\":\"123\"" +
                                                        "}," +
                                                        "\"to\":{" +
                                                            "\"id\":2," +
                                                            "\"latitude\":13.13," +
                                                            "\"longitude\":14.14," +
                                                            "\"address\":\"loc2 \"," +
                                                            "\"city\":\"buc\"," +
                                                            "\"state\":\"B\"," +
                                                            "\"zip\":\"124\"" +
                                                        "}," +
                                                        "\"when\":\"1970-01-01T13:04:00.586+02:00\"," +
                                                        "\"car\":{" +
                                                            "\"id\":1," +
                                                            "\"number\":\"IL11ABC\"," +
                                                            "\"seats\":2" +
                                                        "}," +
                                                        "\"status\":\"PENDING\"," +
                                                        "\"passengers\":[" +
                                                            "{" +
                                                                "\"id\":1," +
                                                                "\"firstName\":\"Ana\"," +
                                                                "\"lastName\":\"Z\"" +
                                                            "}," +
                                                            "{" +
                                                                "\"id\":2," +
                                                                "\"firstName\":\"Maria\"," +
                                                                "\"lastName\":\"Z\"" +
                                                            "}" +
                                                        "]" +
                                                    "}," +
                                                    "\"status\":\"ACCEPTED\"" +
                                                "}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql"})
    @Test
    public void testJoinRide() throws Exception {
        // TODO 2 create a ride request

        mvc.perform(MockMvcRequestBuilders.put("/passengers/4/ride-requests/")
                .content("{" +
                        "\"id\":1" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{" +
                                "\"id\":1," +
                                "\"number\":\"IL11ABC\"," +
                                "\"seats\":2" +
                                "}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql"})
    @Test
    public void testCancelRideRequest() throws Exception {
        // TODO 2 what should we do to cancel a ride request?

        mvc.perform(MockMvcRequestBuilders.put("/passengers/4/ride-requests/")
                .content("{" +
                        "\"id\":1" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.patch("/passengers/4/ride-requests/1/status/")
                .content("CANCELED")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((MockMvcResultMatchers.content())
                    .string("{" +
                                                "\"id\":1," +
                                                "\"passenger\":{" +
                                                    "\"id\":4," +
                                                    "\"firstName\":\"Georgeta\"," +
                                                    "\"lastName\":\"Z\"" +
                                                "}," +
                                                "\"ride\":{" +
                                                    "\"id\":1," +
                                                    "\"from\":{" +
                                                        "\"id\":1," +
                                                        "\"latitude\":11.11," +
                                                        "\"longitude\":12.12," +
                                                        "\"address\":\"loc1 \"," +
                                                        "\"city\":\"buc\"," +
                                                        "\"state\":\"B\"," +
                                                        "\"zip\":\"123\"" +
                                                    "}," +
                                                    "\"to\":{" +
                                                        "\"id\":2," +
                                                        "\"latitude\":13.13," +
                                                        "\"longitude\":14.14," +
                                                        "\"address\":\"loc2 \"," +
                                                        "\"city\":\"buc\"," +
                                                        "\"state\":\"B\"," +
                                                        "\"zip\":\"124\"" +
                                                    "}," +
                                                    "\"when\":\"1970-01-01T13:04:00.586+02:00\"," +
                                                    "\"car\":{" +
                                                        "\"id\":1," +
                                                        "\"number\":\"IL11ABC\"," +
                                                        "\"seats\":2" +
                                                    "}," +
                                                    "\"status\":\"PENDING\"," +
                                                    "\"passengers\":[" +
                                                        "{" +
                                                            "\"id\":1," +
                                                            "\"firstName\":\"Ana\"," +
                                                            "\"lastName\":\"Z\"" +
                                                        "}," +
                                                        "{" +
                                                            "\"id\":2," +
                                                            "\"firstName\":\"Maria\"," +
                                                            "\"lastName\":\"Z\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"status\":\"CANCELED\"" +
                                            "}"))
                .andDo(MockMvcResultHandlers.print());
    }

}
