package sk.tuke.SensorWebApi.server.services.core;

import com.github.rkumsher.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.response.DailyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.http.response.MonthlyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.http.response.TeamsResponse;
import sk.tuke.SensorWebApi.server.http.response.WeeklyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.DailyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.DailyTeamReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.MonthlyTeamReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.TeamRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.WeeklyTeamReportRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService
{
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired private TeamRepository teamRepository;
    @Autowired private DailyTeamReportRepository dailyTeamReportRepository;
    @Autowired private MonthlyTeamReportRepository monthlyTeamReportRepository;
    @Autowired private WeeklyTeamReportRepository weeklyTeamReportRepository;

    public TeamsResponse fetchAll() {
        return new TeamsResponse(teamRepository.findAll());
    }


    public DailyTeamReportsResponse getDailyTeamReports(Date day) {
        return new DailyTeamReportsResponse(dailyTeamReportRepository.findAllByDay(DateUtils.atStartOfDay(day)));
    }

    public MonthlyTeamReportsResponse getMonthlyReports(Date month) {
        return new MonthlyTeamReportsResponse(monthlyTeamReportRepository.findAllByMonth(month));
    }

    public WeeklyTeamReportsResponse getWeeklyReports(Long monthReportID)
    {
        Optional<MonthlyTeamReport> monthlyTeamReport = monthlyTeamReportRepository.findById(monthReportID);
        if (!monthlyTeamReport.isPresent()) return null;
        List<WeeklyTeamReport> weeklyTeamReports = monthlyTeamReport.get().getWeeklyTeamReports();

        return new WeeklyTeamReportsResponse(weeklyTeamReports);
    }

    public DailyTeamReportsResponse getDailyReports(Long weekReportID)
    {
        Optional<WeeklyTeamReport> weeklyTeamReport = weeklyTeamReportRepository.findById(weekReportID);
        if (!weeklyTeamReport.isPresent()) return null;
        List<DailyTeamReport> dailyTeamReports = weeklyTeamReport.get().getDailyTeamReportList();

        return new DailyTeamReportsResponse(dailyTeamReports);
    }



    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<>();
        this.teamRepository.findAll().forEach( t -> teamNames.add(t.getTeamName()));

        return teamNames;
    }


}
