package sk.tuke.SensorWebApi.server.services;

import com.github.rkumsher.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.DailyReport;
import sk.tuke.SensorWebApi.server.entities.DailyTeamReport;
import sk.tuke.SensorWebApi.server.entities.Team;
import sk.tuke.SensorWebApi.server.repositories.DailyTeamReportRepository;
import sk.tuke.SensorWebApi.server.repositories.TeamRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeamService {
    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }



    @Autowired
    private DailyTeamReportRepository dailyTeamReportRepository;

    public TeamsResponse fetchAll() {
        return new TeamsResponse(teamRepository.findAll());
    }

    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<>();

        this.fetchAll().teams.forEach(team -> teamNames.add(team.teamName));

        return teamNames;
    }


    public void generateDailyReport(Team team, List<DailyReport> dailyReports, Date day) {
        float occupation = 0;
        int size = 0;

        for(DailyReport dailyReport : dailyReports) {
            if(dailyReport.getDesk().getTeam() == team) {
                occupation += dailyReport.getAverageOccupation();
                System.out.println(dailyReport.getDesk().getLabel());
                ++size;
            }
        }

        float averageOccupation = occupation / size;

        dailyTeamReportRepository.save(new DailyTeamReport(team, day, averageOccupation));
    }


    public DailyTeamReportsResponse getDailyTeamReports(Date day) {
        return new DailyTeamReportsResponse(dailyTeamReportRepository.findAllByDay(DateUtils.atStartOfDay(day)));
    }


    public class DailyTeamReportsResponse implements Serializable {
        List<DailyTeamReportResponse> dailyTeamReportResponses = new ArrayList<>();

        public DailyTeamReportsResponse(List<DailyTeamReport> dailyTeamReports) {
            dailyTeamReports.forEach( dailyTeamReport -> dailyTeamReportResponses.add(new DailyTeamReportResponse(
                    dailyTeamReport.getTeam().getId(),
                    dailyTeamReport.getTeam().getTeamName(),
                    dailyTeamReport.getAverageOccupation()
            )));
        }

        public List<DailyTeamReportResponse> getDailyTeamReportResponses() {
            return dailyTeamReportResponses;
        }

        public void setDailyTeamReportResponses(List<DailyTeamReportResponse> dailyTeamReportResponses) {
            this.dailyTeamReportResponses = dailyTeamReportResponses;
        }
    }

    public class DailyTeamReportResponse implements Serializable {
        private Long teamId;
        private String teamName;
        private float averageOccupation;

        public DailyTeamReportResponse(Long teamId, String teamName, float averageOccupation) {
            this.teamId = teamId;
            this.teamName = teamName;
            this.averageOccupation = averageOccupation;
        }

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public float getAverageOccupation() {
            return averageOccupation;
        }

        public void setAverageOccupation(float averageOccupation) {
            this.averageOccupation = averageOccupation;
        }
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
