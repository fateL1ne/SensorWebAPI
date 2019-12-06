package sk.tuke.SensorWebApi.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.SensorWebApi.server.services.DeskService;

@RestController
@RequestMapping(value = "/desks", produces = { MediaType.APPLICATION_JSON_VALUE })
public class DeskController
{
    @Autowired
    private DeskService deskService;

    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskService.DesksResponse getAllDesks() {
        return deskService.fetchAll();
    }

    @GetMapping(value ="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskService.DeskResponse getDesk(@PathVariable Long id) {
        return deskService.fetchOne(id);
    }

    @GetMapping(value = "/office/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskService.DesksResponse getDesksPerOffice(@PathVariable Long id) { return deskService.getDesksPerOffice(id); }

    @GetMapping(value = "/team/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskService.DesksResponse getDesksPerTeam(@PathVariable Long id) {
        return deskService.getDesksPerTeam(id);
    }

    @GetMapping(value = "/office/autocomplete/{searchOffice}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskService.DesksResponse getDesksPerOfficeAutocomplete(@PathVariable String searchOffice) {
        return deskService.getDesksPerOfficeAutoComplete(searchOffice);
    }

    @GetMapping(value = "/team/autocomplete/{searchTeam}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeskService.DesksResponse getDesksPerTeamAutocomplete(@PathVariable String searchTeam) {
        return deskService.getDesksPerTeamAutoComplete(searchTeam);
    }
}
