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

    @Sql("/passenger.sql")
    @Test
    public void testFindAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/passengers/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                                "{\"id\":2,\"firstName\":\"Maria\",\"lastName\":\"Z\"}," +
                                                "{\"id\":3,\"firstName\":\"Ioana\",\"lastName\":\"Z\"}]"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Sql("/passenger.sql")
    @Test
    public void testFindOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/passengers/1/")
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
    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void testListRequestsByPassenger() throws Exception {
        // TODO 2 requests for a passenger.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/1/ride-requests/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"passenger\":{\"id\":1,\"firstName\":\"Ana\",\"lastName\":\"Z\"}," +
                                "\"status\":\"ACCEPTED\"}]"))
                .andDo(MockMvcResultHandlers.print());
    }



    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void testJoinRide() throws Exception {
        // TODO 2 create a ride request

        mvc.perform(MockMvcRequestBuilders.put("/passengers/1/ride-requests/")
                .content("{" +
                        "\"rideId\":1" +
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
