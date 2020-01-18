package sk.tuke.SensorWebApi.server.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.SensorWebApi.server.http.response.*;
import sk.tuke.SensorWebApi.server.services.helpers.MockService;
import sk.tuke.SensorWebApi.server.services.core.TeamService;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/teams", consumes = "application/json", produces = "application/json")
public class TeamController {

    @Autowired private TeamService teamService;
    @Autowired private MockService mockService;

    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public TeamsResponse getAllTeams() {
        return teamService.fetchAll();
    }

    @GetMapping(value = "/allTeamNames", produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<String> teamNames() {
        return teamService.getTeamNames();
    }

    @GetMapping(value = "/monthlyReports/{month}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public MonthlyTeamReportsResponse getMonthlyReports(@PathVariable Long month) { return teamService.getMonthlyReports(new Date(month)); }

    @GetMapping(value = "/weeklyReports/{monthReportID}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public WeeklyTeamReportsResponse getWeeklyReports(@PathVariable Long monthReportID) { return teamService.getWeeklyReports(monthReportID); }

    @GetMapping(value = "/dailyReports/{weekReportID}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public DailyTeamReportsResponse getDailyReports(@PathVariable Long weekReportID) { return teamService.getDailyReports(weekReportID); }

    @GetMapping(value = "/monthlyStats/{month}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<MonthlyTeamStatsResponse> getMonthlyStats(@PathVariable Long month) { return teamService.getMonthlyStats(new Date(month)); }

    @GetMapping(value = "/dummy")
    public void dummy() { mockService.init(); }

}
