package sk.tuke.SensorWebApi.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.entities.Office;
import sk.tuke.SensorWebApi.server.entities.Team;
import sk.tuke.SensorWebApi.server.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.repositories.OfficeRepository;
import sk.tuke.SensorWebApi.server.repositories.TeamRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeskService
{

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

    public class DeskResponse implements Serializable {

        private Long id;
        private String officeName;
        private String teamName;

        DeskResponse(Desk desk) {
            this.id = desk.getId();
            this.officeName = desk.getOffice().getOfficeName();
            this.teamName = desk.getTeam().getTeamName();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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
