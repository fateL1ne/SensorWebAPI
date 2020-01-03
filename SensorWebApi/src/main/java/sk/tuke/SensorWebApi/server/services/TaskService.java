package sk.tuke.SensorWebApi.server.services;

import com.github.rkumsher.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.MonthlyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.ReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.TeamRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.MonthlyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.MonthlyTeamReportRepository;
import sk.tuke.SensorWebApi.server.services.core.TeamService;
import sk.tuke.SensorWebApi.server.services.report.DailyReportService;
import sk.tuke.SensorWebApi.server.services.report.MonthlyReportService;
import sk.tuke.SensorWebApi.server.services.report.WeeklyReportService;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TaskService
{
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static final long DAY = 24 * 60 * 60 * 1000;
    private static final long WEEK = DAY * 7;



    @Autowired private DeskRepository deskRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private DailyReportService dailyReportService;
    @Autowired private WeeklyReportService weeklyReportService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private DailyReportRepository dailyReportRepository;
    @Autowired private MonthlyReportService monthlyReportService;
    @Autowired private TeamService teamService;
    @Autowired private MonthlyReportRepository monthlyReportRepository;
    @Autowired private MonthlyTeamReportRepository monthlyTeamReportRepository;


    @Scheduled(cron = "0 5 0 * * *", zone = "Europe/Bratislava")
    public void generateDailyReports() {
        logger.info("Running daily reports task");

        Date yesterday = new Date(System.currentTimeMillis() - DAY);
        List<Desk> allDesks = deskRepository.findAll();
        allDesks.forEach( desk -> dailyReportService.generateReport(desk, yesterday));

        List<Team> teams = teamRepository.findAll();
        List<DailyReport> generatedReports = dailyReportRepository.findAllByDay(yesterday);

        teams.forEach( team -> teamService.generateDailyReport(team, generatedReports, yesterday));
    }

    @Scheduled(cron = "0 0 1 * * MON")
    public void generateWeeklyReports() {
        logger.info("Running weekly reports task");

        Date startOfWeek = DateUtils.atStartOfDay(new Date(System.currentTimeMillis() - WEEK));
        Date endOfWeek = DateUtils.atStartOfDay(new Date(System.currentTimeMillis()));

        List<Desk> allDesks = deskRepository.findAll();
        allDesks.forEach( desk -> weeklyReportService.generateReport(desk, startOfWeek, endOfWeek));
    }

    @Scheduled(cron = "0 0 2 1 * *")
    public void generateMonthlyReports() {
        logger.info("Running monthly reports task");

        Date lastDay = DateUtils.atStartOfDay(new Date(System.currentTimeMillis() - DAY));

        Date startOfLastMonth = Date.from(lastDay.toInstant().atZone(
                ZoneId.systemDefault()).withDayOfMonth(1).toInstant());

        List<Desk> allDesks = deskRepository.findAll();

        allDesks.forEach( desk ->  monthlyReportService.generateDeskReport(desk, startOfLastMonth, new Date()));
    }


//    @Scheduled(cron = "0 30 2 1 * *")
    public void generateMonthlyTeamReports() {
        logger.info("Running monthly team reports task");

        // TODO: 1/3/20 FETCH ALL FOR THE SPECIFIC MONTH

        List<Team> teams = teamRepository.findAll();

        teams.forEach( T -> {

            List<MonthlyReport> monthlyReports = monthlyReportRepository.findAllByTeam(T);

            if (!monthlyReports.isEmpty()) {

                float averageOccupation = 0.00f;

                for (MonthlyReport monthlyReport : monthlyReports)
                    averageOccupation += monthlyReport.getAverageOccupation();

                averageOccupation /= monthlyReports.size();
                monthlyTeamReportRepository.save(new MonthlyTeamReport(T, averageOccupation, monthlyReports.get(0).getMonth()));
            }
        });
    }

    @Scheduled(cron = "0 0/30 * * * *", zone = "Europe/Bratislava")
    public void mockReport() {
        logger.info("Running mock data task");

        List<Desk> allDesks = deskRepository.findAll();
        Random random = new Random();

        allDesks.forEach( (desk -> reportRepository.save(new Report(desk, new Date(), random.nextInt() % 2 == 0))));
    }

}
