package sk.tuke.SensorWebApi.server.services.core;

import com.github.rkumsher.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.response.DailyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.http.response.MonthlyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.http.response.TeamsResponse;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.DailyTeamReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.MonthlyTeamReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.TeamRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeamService
{
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired private TeamRepository teamRepository;
    @Autowired private DailyTeamReportRepository dailyTeamReportRepository;
    @Autowired private MonthlyTeamReportRepository monthlyTeamReportRepository;

    public TeamsResponse fetchAll() {
        return new TeamsResponse(teamRepository.findAll());
    }


    public DailyTeamReportsResponse getDailyTeamReports(Date day) {
        return new DailyTeamReportsResponse(dailyTeamReportRepository.findAllByDay(DateUtils.atStartOfDay(day)));
    }

    public MonthlyTeamReportsResponse getMonthlyReports(Date month) {
        return new MonthlyTeamReportsResponse(monthlyTeamReportRepository.findAll());
    }

    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<>();
        this.teamRepository.findAll().forEach( t -> teamNames.add(t.getTeamName()));

        return teamNames;
    }


}
