package com.ing.switchtojava.carpoolingapi.rest;


import com.ing.switchtojava.carpoolingapi.CarPoolingApiApplication;
import com.ing.switchtojava.carpoolingapi.domain.Location;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.repository.*;
import com.ing.switchtojava.carpoolingapi.service.CarPositionService;
import com.ing.switchtojava.carpoolingapi.service.CarService;
import com.ing.switchtojava.carpoolingapi.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "user", password = "password", roles = "USER")
public class LocationControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    LocationService locationService;

    @MockBean
    LocationRepository repository;

    @Test
    public void getLocations() throws Exception {
        Location a = new Location();
        a.setId(1L);
        a.setLatitude(44.4513003);
        a.setLongitude(26.0415585);
        a.setAddress("Crangasi");
        a.setCity("Bucuresti");
        a.setZip("123-123");
        a.setState("B");
        Mockito.when(repository.findAll())
                .thenReturn(asList(a));

        mvc.perform(MockMvcRequestBuilders.get("/locations/")
                                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                        .string("[{" +
                                        "\"id\":1," +
                                        "\"latitude\":44.4513003," +
                                        "\"longitude\":26.0415585," +
                                        "\"address\":\"Crangasi\"," +
                                        "\"city\":\"Bucuresti\"," +
                                        "\"state\":\"B\"," +
                                        "\"zip\":\"123-123\"" +
                                        "}]"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(repository).findAll();
    }
}
