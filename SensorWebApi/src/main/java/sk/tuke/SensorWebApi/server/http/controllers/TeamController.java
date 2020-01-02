package sk.tuke.SensorWebApi.server.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.SensorWebApi.server.http.response.DailyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.http.response.MonthlyTeamReportsResponse;
import sk.tuke.SensorWebApi.server.http.response.TeamsResponse;
import sk.tuke.SensorWebApi.server.services.MockService;
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

    @GetMapping(value = "/dailyReports/{day}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public DailyTeamReportsResponse getDailyReports(@PathVariable Long day) { return teamService.getDailyTeamReports(new Date(day)); }

    @GetMapping(value = "/monthlyReports/{month}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public MonthlyTeamReportsResponse getMonthlyReports(@PathVariable Long month) { return teamService.getMonthlyReports(new Date(month)); }


    @GetMapping(value = "/dummy")
    public void dummy() {

       mockService.init();
    }

}
