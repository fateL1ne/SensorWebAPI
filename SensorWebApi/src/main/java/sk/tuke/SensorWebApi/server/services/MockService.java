package sk.tuke.SensorWebApi.server.services;

import com.github.rkumsher.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Report;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.ReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.TeamRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.WeeklyReportRepository;
import sk.tuke.SensorWebApi.server.services.core.TeamService;
import sk.tuke.SensorWebApi.server.services.report.DailyReportService;
import sk.tuke.SensorWebApi.server.services.report.MonthlyReportService;
import sk.tuke.SensorWebApi.server.services.report.WeeklyReportService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class MockService
{

    private static final long HALF_HOUR = 30 * 60 * 1000;

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
    @Autowired private WeeklyReportRepository weeklyReportRepository;


    public void init() {
        Calendar calendarEnd=Calendar.getInstance();
        calendarEnd.set(Calendar.YEAR,2019);
        calendarEnd.set(Calendar.MONTH,1);
        calendarEnd.set(Calendar.DAY_OF_MONTH,1);
        calendarEnd.set(Calendar.HOUR, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);

        Date D1 =calendarEnd.getTime();

        calendarEnd.set(Calendar.MONTH, 2);

        Date D2 =calendarEnd.getTime();

        genWeekReport(D1, D2);
    }

    public  void mockME(Date d1, Date d2) {


        List<Desk> allDesks = deskRepository.findAll();
        long m1  = d1.getTime();
        long m2 = d2.getTime();
        Random random;

        System.out.println("[!] mocking regular reports for " + allDesks.size() + " desks");


        for (Desk desk : allDesks) {
            for (long i = m1; i <= m2; i += HALF_HOUR) {
                random = new Random();
                reportRepository.save(new Report(desk, new Date(i), random.nextInt() % 2 == 0));
            }
            System.out.println("[!] finish for desk with label :" + desk.getLabel());

        }
    }


    public  void genDayReport(Date d1, Date d2) {

        List<Desk> allDesks = deskRepository.findAll();

        long m1  = d1.getTime();
        long m2 = d2.getTime();


        for (Desk desk : allDesks) {
            for (long i = m1; i <= m2; i += DAY) {
                dailyReportService.generateReport(desk, new Date(i));
            }
        }
    }

    public  void genWeekReport(Date d1, Date d2) {
        List<Desk> allDesks = deskRepository.findAll();

        long m1  = d1.getTime();
        long m2 = d2.getTime();

        for (Desk desk : allDesks) {
            for (long i = m1; i <= m2; i += WEEK) {
                weeklyReportService.generateReport(desk, new Date(i), DateUtils.atEndOfDay(new Date(i + WEEK - 3600)));
            }
        }
    }

    public  void genMonReport(Date d1, Date d2) {
        List<Desk> allDesks = deskRepository.findAll();

        long m1  = d1.getTime();
        long m2 = d2.getTime();

        for (Desk desk : allDesks) {
            for (long i = m1; i <= m2; i += DAY*30) {
                monthlyReportService.generateDeskReport(desk, new Date(i), new Date(i+DAY*30) );
            }
        }
    }

}
