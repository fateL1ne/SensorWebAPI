package sk.tuke.SensorWebApi.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.Team;
import sk.tuke.SensorWebApi.server.repositories.TeamRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamsResponse fetchAll() {
        return new TeamsResponse(teamRepository.findAll());
    }

    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<>();

        this.fetchAll().teams.forEach(team -> teamNames.add(team.teamName));

        return teamNames;
    }

    public class TeamResponse implements Serializable {

        private Long id;
        private String teamName;
        private String leader;

        TeamResponse(Team team) {
            this.id = team.getId();
            this.teamName = team.getTeamName();
            this.leader = team.getLeader();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }
    }

    public class TeamsResponse {
        private final List<TeamResponse> teams = new ArrayList<>();

        TeamsResponse(List<Team> teams) {
            teams.forEach(team -> this.teams.add(new TeamResponse(team)));
        }

        public List<TeamResponse> getTeams() {
            return teams;
        }
    }
}
