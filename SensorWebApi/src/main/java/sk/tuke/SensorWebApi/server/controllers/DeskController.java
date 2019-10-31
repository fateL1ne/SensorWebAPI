package sk.tuke.SensorWebApi.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.services.jpa.DeskRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @project SensorWebApi
 * @author jabu
 * @date 10/31/19
 */

@RestController
@RequestMapping(value = "/desk_monitoring", produces = { MediaType.APPLICATION_JSON_VALUE })
public class DeskController
{
    @Autowired
    private DeskRepository deskRepositoryService;

    @GetMapping(value = "/desks")
    public List<Desk> getAllDesks() {
        return deskRepositoryService.findAll();
    }

    @GetMapping(value ="/desk/{id}")
    public Desk getDesk(@PathVariable Long id) {
        return deskRepositoryService.getOne(id);
    }

    public DeskRepository getDeskRepositoryService() { return deskRepositoryService; }
    public void setDeskRepositoryService(DeskRepository deskRepositoryService) { this.deskRepositoryService = deskRepositoryService; }
}
