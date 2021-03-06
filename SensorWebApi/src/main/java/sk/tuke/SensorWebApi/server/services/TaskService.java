package sk.tuke.SensorWebApi.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Office;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.OfficeRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.ReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.TeamRepository;
import sk.tuke.SensorWebApi.server.services.helpers.DateService;
import sk.tuke.SensorWebApi.server.services.report.DailyReportService;
import sk.tuke.SensorWebApi.server.services.report.MonthlyReportService;
import sk.tuke.SensorWebApi.server.services.report.WeeklyReportService;
import sk.tuke.SensorWebApi.server.services.suggestion.desks.SuggestionService;
import sk.tuke.SensorWebApi.server.services.suggestion.teams.TeamStatsService;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TaskService
{
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired private DateService dateService;
    @Autowired private DeskRepository deskRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private DailyReportService dailyReportService;
    @Autowired private WeeklyReportService weeklyReportService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private MonthlyReportService monthlyReportService;
    @Autowired private OfficeRepository officeRepository;
    @Autowired private SuggestionService suggestionService;
    @Autowired private TeamStatsService teamStatsService;


    @Scheduled(cron = "0 5 0 * * *", zone = "Europe/Bratislava")
    public void generateDailyReports()
    {
        logger.info("Generating daily desk reports");

        Date yesterday = dateService.getYesterday();
        List<Desk> allDesks = deskRepository.findAll();
        allDesks.forEach( desk -> dailyReportService.generateDeskReport(desk, yesterday) );

        logger.info("Generating daily team reports");

        List<Team> allTeams = teamRepository.findAll();
        allTeams.forEach( team -> dailyReportService.generateTeamReport(team, yesterday));
    }

    @Scheduled(cron = "0 0 1 * * MON", zone = "Europe/Bratislava")
    public void generateWeeklyReports() {
        logger.info("Generating weekly desk reports");

        Date startOfWeek = dateService.getStartOfLastWeek();
        Date endOfWeek = dateService.getEndOfLastWeek();

        List<Desk> allDesks = deskRepository.findAll();
        allDesks.forEach( desk -> weeklyReportService.generateDeskReport(desk, startOfWeek, endOfWeek));

        logger.info("Generating  weekly desk reports");

        List<Team> allTeams = teamRepository.findAll();
        allTeams.forEach( team -> weeklyReportService.generateTeamReport(team, startOfWeek, endOfWeek));

    }

    @Scheduled(cron = "0 0 1 1 * *", zone = "Europe/Bratislava")
    public void generateMonthlyReports() {
        logger.info("Generating monthly desk reports");

        Date startOfMonth = dateService.getStartOfLastMonth();
        Date endOfMonth = dateService.getEndOfLastMonth();

        List<Desk> allDesks = deskRepository.findAll();
        allDesks.forEach( desk -> monthlyReportService.generateDeskReport(desk, startOfMonth, endOfMonth));

        logger.info("Generating monthly team reports");

        List<Team> allTeams = teamRepository.findAll();
        allTeams.forEach( team -> monthlyReportService.generateTeamReport(startOfMonth, endOfMonth, team));
    }


//    @Scheduled(cron = "0 * * * * *", zone = "Europe/Bratislava")
//    @Scheduled(cron = "0 0 2 1 * *", zone = "Europe/Bratislava")
    public void generateMonthlySuggestions() {
        logger.info("Generating monthly suggestions");

        Date startOfMonth = dateService.getSpecificDay(2019, 1, 1);
        Date endOfMonth = dateService.getSpecificDay(2019, 2, 0);

        List<Office> offices = officeRepository.findAll();

        offices.forEach( office -> suggestionService.generateOfficeSuggestion(office, startOfMonth, endOfMonth));

//        List<Team> teams = teamRepository.findAll();
//
//        teams.forEach( team ->  teamStatsService.generateTeamStats(startOfMonth, endOfMonth, team));
    }

    @Scheduled(cron = "0 0/30 * * * *", zone = "Europe/Bratislava")
    public void mockReport() {
        logger.info("Running mock data task");

        List<Desk> allDesks = deskRepository.findAll();
        Random random = new Random();

        allDesks.forEach( (desk -> reportRepository.save(new Report(desk, new Date(), random.nextInt() % 2 == 0))));
    }

}
