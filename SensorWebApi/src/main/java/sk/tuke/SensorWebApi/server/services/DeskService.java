package sk.tuke.SensorWebApi.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.entities.Office;
import sk.tuke.SensorWebApi.server.entities.Team;
import sk.tuke.SensorWebApi.server.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.repositories.OfficeRepository;
import sk.tuke.SensorWebApi.server.repositories.TeamRepository;
import sk.tuke.SensorWebApi.server.request.NewDesk;
import sk.tuke.SensorWebApi.server.request.PutDeskRequest;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeskService
{

    private final static boolean SUCCESS = true;
    private final static boolean FAILED = false;

    private final Logger logger = LoggerFactory.getLogger(DeskService.class);

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private TeamRepository teamRepository;

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
        String teamName = newDesk.getTeamName();
        String officeName = newDesk.getOfficeName();
        Office office;
        Team team;

        try {
            office = officeRepository.findOneByOfficeName(officeName);
            team = teamRepository.findOneByTeamName(teamName);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        deskRepository.save(new Desk(generateLabel(office), team, office));

        return new ResponseEntity(HttpStatus.OK);
    }


    private String generateLabel(Office office) {
        int actualDesksPerOffice = office.getDesks().size();
        String officeName = office.getOfficeName();

        if (actualDesksPerOffice < 9)
            return officeName + "0" + (actualDesksPerOffice + 1);
        else
            return officeName + (actualDesksPerOffice + 1);
    }


    public ResponseEntity editTeam(PutDeskRequest putDeskRequest) {
        Long id = putDeskRequest.getDeskId();
        String newName = putDeskRequest.getNewName();
        Desk desk;
        Team team;

        try {
            desk = deskRepository.getOne(id);
            team = teamRepository.findOneByTeamName(newName);
            desk.setTeam(team);
        } catch (EntityNotFoundException e) {
            logger.error("Can't assign the new team name " + newName + " for desk with ID: " + id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        deskRepository.save(desk);

        return new ResponseEntity(HttpStatus.OK);
    }

    public class DeskResponse implements Serializable {

        private Long id;
        private String label;
        private String officeName;
        private String teamName;

        DeskResponse(Desk desk) {
            this.id = desk.getId();
            this.label = desk.getLabel();
            this.officeName = desk.getOffice().getOfficeName();
            this.teamName = desk.getTeam().getTeamName();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLabel() { return label; }

        public void setLabel(String label) { this.label = label; }

        public String getOfficeName() {
            return officeName;
        }

        public void setOfficeName(String officeName) {
            this.officeName = officeName;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
    }

    public class DesksResponse {
        private final List<DeskResponse> desks = new ArrayList<>();

        DesksResponse(List<Desk> desks) {
            desks.forEach(desk -> this.desks.add(new DeskResponse(desk)));
        }

        public List<DeskResponse> getDesks() {
            return desks;
        }
    }
}
