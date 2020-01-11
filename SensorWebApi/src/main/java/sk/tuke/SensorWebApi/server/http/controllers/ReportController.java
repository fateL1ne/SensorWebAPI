package sk.tuke.SensorWebApi.server.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.SensorWebApi.server.http.request.ReportRequest;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.services.report.ReportService;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/reporting", consumes = "application/json", produces = "application/json")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @PostMapping(value = "/add")
    public ResponseEntity<HttpStatus> newReport(@RequestBody ReportRequest incomingRequest) {
        reportService.addReport(incomingRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping(value = "/dailyTeam/{day}/{team}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<DailyReport> teamNames(@PathVariable Date day, @PathVariable Team team) {
        return dailyReportRepository.findAllByDayAndDesk_Team(day, team);
    }

    @GetMapping(value = "/reports/daily/{day}/{teamId}", produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<DailyReport> dailyReportsByDateAndTeamId(@PathVariable long day, @PathVariable long teamId) {
        return dailyReportRepository.findAllByDayAndDeskTeamId(new Date(day), teamId);
    }
}
