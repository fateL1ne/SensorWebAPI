package sk.tuke.SensorWebApi.server.services.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.response.DeskResponse;
import sk.tuke.SensorWebApi.server.http.response.DesksResponse;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Office;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.OfficeRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.TeamRepository;
import sk.tuke.SensorWebApi.server.http.request.NewDesk;
import sk.tuke.SensorWebApi.server.http.request.PutDeskRequest;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeskService
{
    private final Logger logger = LoggerFactory.getLogger(DeskService.class);

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private TeamService teamService;

    public DeskService() { }

    public DesksResponse fetchAll() {
        return new DesksResponse(deskRepository.findAll());
    }

    public DeskResponse fetchOne(Long id) {
        return new DeskResponse(deskRepository.getOne(id));
    }

    public DesksResponse getDesksPerOffice(Long id) {
        return new DesksResponse(deskRepository.findByOfficeId(id));
    }

    public DesksResponse getDesksPerTeam(Long id) {
        return new DesksResponse(deskRepository.findByTeamId(id));
    }


    public DesksResponse getDesksPerOfficeAutoComplete(String searchOffice) {
        List<Office> offices = officeRepository.findAllByOfficeNameStartingWith(searchOffice);
        List<Desk> desks = new ArrayList<>();
        offices.forEach( office -> desks.addAll(office.getDesks()));

        return new DesksResponse(desks);
    }

    public DesksResponse getDesksPerTeamAutoComplete(String searchTeam) {
        List<Team> teams = teamRepository.findAllByTeamNameStartingWith(searchTeam);
        List<Desk> desks = new ArrayList<>();
        teams.forEach( team -> desks.addAll(team.getDesks()));

        return new DesksResponse(desks);
    }

    public ResponseEntity addDesk(NewDesk newDesk) {
        // trim leading and trailing whitespaces
        String deskLabel = newDesk.getDeskLabel().trim();
        String teamName = newDesk.getTeamName();
        String officeName = newDesk.getOfficeName();
        Office office;
        Team team;

        if(!newDesk.isValid()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //if desk with this label already exists
        if(deskRepository.findDeskByLabel(deskLabel) != null) {
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
        }

        office = officeRepository.findOneByOfficeName(officeName);
        team = teamRepository.findOneByTeamName(teamName);
        //find if request contains office and team that exists in database
        if(office == null || team == null) {
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }

        deskRepository.save(new Desk(deskLabel, team, office));

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity editDesk(PutDeskRequest putDeskRequest) {
        Long id = putDeskRequest.getDeskId();
        String newTeam = putDeskRequest.getNewTeam();
        String newOffice = putDeskRequest.getNewOffice();
        Desk desk;
        Team team;
        Office office;

        if(!putDeskRequest.isValid()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            desk = deskRepository.getOne(id);
            team = teamRepository.findOneByTeamName(newTeam);
            office = officeRepository.findOneByOfficeName(newOffice);
            desk.setTeam(team);
            desk.setOffice(office);
        } catch (EntityNotFoundException | NullPointerException e) {
            logger.error("Can't assign the new team: " + newTeam + " or new office: " + newOffice + " for desk with ID: " + id);
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        team.getDesks().add(desk);
        office.getDesks().add(desk);
        deskRepository.save(desk);

        return new ResponseEntity(HttpStatus.OK);
    }

}
