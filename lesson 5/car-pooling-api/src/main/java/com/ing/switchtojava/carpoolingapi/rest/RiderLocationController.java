package com.ing.switchtojava.carpoolingapi.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ing.switchtojava.carpoolingapi.service.RiderCoordonates;
import com.ing.switchtojava.carpoolingapi.service.RiderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/riderLocation/")
public class RiderLocationController {

    @Autowired
    RiderLocationService riderLocationService;

    @GetMapping("rides/{id}/track")
    public RiderCoordonates getPosition (@PathVariable Long id)
    {
        return  riderLocationService.getNextPoint();
    }

    @GetMapping("rides/{id}/trackJson")
    public String getPosition () throws JsonProcessingException {
        return  riderLocationService.getNextPointAsJson();
    }

    @GetMapping("rides/{id}/tracking")
    public SseEmitter streamSSeMvc(@PathVariable Long id) throws JsonProcessingException {
        SseEmitter emiter = riderLocationService.track(id);
        return  emiter;
    }
}
