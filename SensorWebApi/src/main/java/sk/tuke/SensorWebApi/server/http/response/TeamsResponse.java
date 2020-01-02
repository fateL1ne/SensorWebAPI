package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamsResponse {

    private final List<TeamResponse> teams = new ArrayList<>();

    public TeamsResponse(List<Team> teams) {
        teams.forEach(team -> this.teams.add(new TeamResponse(team)));
    }

    public List<TeamResponse> getTeams() {
        return teams;
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
}
