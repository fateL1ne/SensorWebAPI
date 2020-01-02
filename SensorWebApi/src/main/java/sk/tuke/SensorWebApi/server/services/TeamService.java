package sk.tuke.SensorWebApi.server.services;

import com.github.rkumsher.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.controllers.response.DailyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.controllers.response.MonthlyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.controllers.response.TeamsResponse;
import sk.tuke.SensorWebApi.server.entities.DailyReport;
import sk.tuke.SensorWebApi.server.entities.DailyTeamReport;
import sk.tuke.SensorWebApi.server.entities.Team;
import sk.tuke.SensorWebApi.server.repositories.DailyTeamReportRepository;
import sk.tuke.SensorWebApi.server.repositories.MonthlyTeamReportRepository;
import sk.tuke.SensorWebApi.server.repositories.TeamRepository;

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


    @Autowired private DailyTeamReportRepository dailyTeamReportRepository;
    @Autowired private MonthlyTeamReportRepository monthlyTeamReportRepository;


    public TeamsResponse fetchAll() {
        return new TeamsResponse(teamRepository.findAll());
    }

    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<>();
        this.teamRepository.findAll().forEach( t -> teamNames.add(t.getTeamName()));

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


    public MonthlyTeamReportsResponse getMonthlyReports(Date month) {
        return new MonthlyTeamReportsResponse(monthlyTeamReportRepository.findAllByMonth(month));
    }

}
