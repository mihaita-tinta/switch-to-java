package com.ing.switchtojava.carpoolingapi.rest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void testFindAll() throws Exception {
        // TODO 2 list all passengers.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testFindOne() throws Exception {
        // TODO 2 list one passenger.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/{1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSaveDriver() throws Exception {
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

    @Test
    public void testListRequestsByPassenger() throws Exception {
        // TODO 2 requests for a passenger.

        mvc.perform(MockMvcRequestBuilders.get("/passengers/1/ride-requests/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[]"))
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
