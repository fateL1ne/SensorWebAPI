package sk.tuke.SensorWebApi.server.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import sk.tuke.SensorWebApi.server.http.request.NewDesk;
import sk.tuke.SensorWebApi.server.http.request.PutDeskRequest;
import sk.tuke.SensorWebApi.server.http.response.DeskResponse;
import sk.tuke.SensorWebApi.server.http.response.DesksResponse;
import sk.tuke.SensorWebApi.server.http.response.SuggestionResponse;
import sk.tuke.SensorWebApi.server.services.core.DeskService;
import sk.tuke.SensorWebApi.server.services.suggestion.desks.SuggestionService;

import java.util.Date;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/desks", produces = { MediaType.APPLICATION_JSON_VALUE })
public class DeskController
{
    @Autowired private DeskService deskService;
    @Autowired private SuggestionService suggestionService;

    @GetMapping(value = "/suggestions/{month}/{day}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<SuggestionResponse> getSuggestions(@PathVariable Long month, @PathVariable String day) {
        return suggestionService.getSuggestionsByMonthAndDay(new Date(month), day);
    }

    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DesksResponse getAllDesks() {
        return deskService.fetchAll();
    }

    @GetMapping(value ="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskResponse getDesk(@PathVariable Long id) {
        return deskService.fetchOne(id);
    }

    @GetMapping(value = "/office/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DesksResponse getDesksPerOffice(@PathVariable Long id) { return deskService.getDesksPerOffice(id); }

    @GetMapping(value = "/team/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DesksResponse getDesksPerTeam(@PathVariable Long id) {
        return deskService.getDesksPerTeam(id);
    }

    @PutMapping(value = "/edit")
    public ResponseEntity editDesk(@RequestBody PutDeskRequest putDeskRequest) { return deskService.editDesk(putDeskRequest); }

    @PostMapping(value = "/add")
    public ResponseEntity addDesk(@RequestBody NewDesk newDesk) { return deskService.addDesk(newDesk); }

    @GetMapping(value = "/office/autocomplete/{searchOffice}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DesksResponse getDesksPerOfficeAutocomplete(@PathVariable String searchOffice) {
        return deskService.getDesksPerOfficeAutoComplete(searchOffice);
    }

    @GetMapping(value = "/team/autocomplete/{searchTeam}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DesksResponse getDesksPerTeamAutocomplete(@PathVariable String searchTeam) {
        return deskService.getDesksPerTeamAutoComplete(searchTeam);
    }

    /**
     *  PRE-FLIGHT REQUEST HANDLER
     */

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() { return new ResponseEntity(HttpStatus.OK); }
}
