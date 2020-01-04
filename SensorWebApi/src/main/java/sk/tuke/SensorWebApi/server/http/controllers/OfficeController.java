package sk.tuke.SensorWebApi.server.http.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.SensorWebApi.server.services.core.OfficeService;

import java.util.List;

@RestController
@RequestMapping(value = "/offices", consumes = "application/json", produces = "application/json")
public class OfficeController {

    @Autowired private OfficeService officeService;

    @GetMapping(value = "/names", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<String> officeNames() { return officeService.getOfficeNames(); }

}
