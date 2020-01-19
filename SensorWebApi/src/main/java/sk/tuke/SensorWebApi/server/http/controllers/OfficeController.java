package sk.tuke.SensorWebApi.server.http.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.SensorWebApi.server.http.response.OfficeSuggestionsResponse;
import sk.tuke.SensorWebApi.server.http.response.SuggestionResponse;
import sk.tuke.SensorWebApi.server.services.core.OfficeService;
import sk.tuke.SensorWebApi.server.services.suggestion.desks.SuggestionService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/offices", consumes = "application/json", produces = "application/json")
public class OfficeController {

    @Autowired private OfficeService officeService;
    @Autowired private SuggestionService suggestionService;

    @GetMapping(value = "/names", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<String> officeNames() { return officeService.getOfficeNames(); }


    @GetMapping(value = "/suggestions/{month}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public OfficeSuggestionsResponse officeNames(@PathVariable long month) { return suggestionService.getOfficesSuggestions(new Date(month)); }


    @GetMapping(value = "/suggestions/{month}/{day}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<SuggestionResponse> findAllByMonthAndDayAndOffice(
            @PathVariable long month, @PathVariable String day) {
            return suggestionService.getSuggestionsByMonthAndDay(new Date(month), day);
    }


}
