package sk.tuke.SensorWebApi.server.services;

import com.github.rkumsher.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.*;
import sk.tuke.SensorWebApi.server.repositories.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TaskService
{
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static final long DAY = 24 * 60 * 60 * 1000;
    private static final long WEEK = DAY * 7;

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private DailyReportService dailyReportService;

    @Autowired
    private WeeklyReportService weeklyReportService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private TeamService teamService;

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

    @Scheduled(cron = "0 0/30 * * * *", zone = "Europe/Bratislava")
    public void mockReport() {
        logger.info("Running mock data task");

        List<Desk> allDesks = deskRepository.findAll();
        Random random = new Random();

        allDesks.forEach( (desk -> reportRepository.save(new Report(desk, new Date(), random.nextInt() % 2 == 0))));
    }

}
