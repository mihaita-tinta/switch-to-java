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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PassengerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql("/passenger.sql")
    public void testFindAll() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/passengers/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[" +
                                                "{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                                "{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"}," +
                                                "{\"id\":3,\"firstName\":\"Ioana\",\"lastName\":\"Z\"}" +
                                                "]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("/passenger.sql")
    public void testFindOne() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/passengers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSaveDriver() throws Exception {

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

    @Test
    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    public void testListRequestsByPassenger() throws Exception {
        // TODO 2 requests for a passenger.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/1/ride-requests/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[" +
                                "{\"id\":1,\"" +
                                "passenger\":" +
                                    "{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                "\"ride\":" +
                                    "{\"id\":1," +
                                    "\"from\":" +
                                        "{\"id\":1,\"latitude\":11.11,\"longitude\":12.12,\"address\":\"loc1 \",\"city\":\"buc\",\"state\":\"B\",\"zip\":\"123\"}," +
                                    "\"to\":" +
                                        "{\"id\":2,\"latitude\":13.13,\"longitude\":14.14,\"address\":\"loc2 \",\"city\":\"buc\",\"state\":\"B\",\"zip\":\"124\"}," +
                                    "\"when\":\"1970-01-01T23:29:46.123+02:00\"," +
                                    "\"car\":" +
                                        "{\"id\":1,\"number\":\"IL11ABC\",\"seats\":2}," +
                                    "\"status\":\"PENDING\"," +
                                    "\"passengers\":[" +
                                        "{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                        "{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"}" +
                                    "]}," +
                                "\"status\":\"ACCEPTED\"}" +
                                "]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testJoinRide() throws Exception {
        // TODO 2 create a ride request

        mvc.perform(MockMvcRequestBuilders.put("/passengers/1/ride-requests/")
                .content("{" +
                        "\"rideId\":2" +
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

    @Test
    public void testCancelRideRequest() throws Exception {
        // TODO 2 what should we do to cancel a ride request?
    }

}
