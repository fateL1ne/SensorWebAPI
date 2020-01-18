package sk.tuke.SensorWebApi.server.services.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.TeamRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.DeskRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.ReportRepository;
import sk.tuke.SensorWebApi.server.services.report.DailyReportService;
import sk.tuke.SensorWebApi.server.services.report.MonthlyReportService;
import sk.tuke.SensorWebApi.server.services.report.WeeklyReportService;

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
    @Autowired private MonthlyReportService monthlyReportService;
    @Autowired private DateService dateService;
    @Autowired private TeamRepository teamRepository;

    public void init() {
        List<Desk> desks = deskRepository.findAll();
        List<Team> teams = teamRepository.findAll();


        //mockMe();
    }

    public void mockMe(Date start, Date end, List<Desk> desks, List<Team> teams)
    {
        mockRegularReports(start, end, desks);
        mockDailyDeskReports(start, end, desks);
        mockWeeklyDeskReports(start, end, desks);
        mockMonthlyDeskReports(start, end, desks);

        mockDailyTeamReports(start, end, teams);
        mockWeeklyTeamReports(start, end, teams);
        mockMonthlyTeamReports(start, end, teams);
    }



    public void mockRegularReports(Date d1, Date d2, List<Desk> desks)
    {
        System.out.println("Mocking regular reports");

        long startMillis = d1.getTime();
        long endMillis = d2.getTime();
        int size = desks.size();

        Random random = new Random();

        for(int deskIdx = 0; deskIdx < size; deskIdx++)
        {
            for (long currentMillis =  startMillis; currentMillis < endMillis; currentMillis+=HALF_HOUR)
            {
                reportRepository.save(new Report(desks.get(deskIdx), new Date(currentMillis), random.nextInt() % 2 == 0));
            }
            System.out.println( (deskIdx+1) + "/" + size + " ... DONE");
        }
    }

    /**
     *  DAILY REPORT SERVICE -----> CHANGE DATE PARAMETER IN FETCH METHOD BEFORE MOCK METHOD
     *
     * @ --> change new Date() to mock-time plus DAY interval
     * @ --> otherwise we can fetch huge numbers of weekly reports between mocked date and the present
     *
     *  reportRepository.findAllByTimestampBetweenAndDesk(day, new Date(), desk)
     *      \   \   \   \   \   |   |   |   |
     *      V   V   V   V   V   V   V   V   V
     *   reportRepository.findAllByTimestampBetweenAndDesk(day, new Date( day.getTime() + DAY ), desk)
     *
     */

    public void mockDailyDeskReports(Date d1, Date d2, List<Desk> desks)
    {
        System.out.println("Mocking daily desk reports");

        long startMillis = d1.getTime();
        long endMillis = d2.getTime();
        int size = desks.size();


        for(int deskIdx = 0; deskIdx < size; deskIdx++)
        {
            for (long currentMillis =  startMillis; currentMillis < endMillis; currentMillis+=DAY)
            {
                dailyReportService.generateDeskReport(desks.get(deskIdx), new Date(currentMillis));
            }
            System.out.println( (deskIdx+1) + "/" + size + " ... DONE");
        }
    }

    public void mockDailyTeamReports(Date d1, Date d2, List<Team> teams)
    {
        System.out.println("Mocking daily team reports");

        long startMillis = d1.getTime();
        long endMillis = d2.getTime();
        int size = teams.size();

        for (int teamIdx = 0; teamIdx< size; teamIdx++)
        {
            for(long currentMillis = startMillis; currentMillis < endMillis; currentMillis+=DAY)
            {
                dailyReportService.generateTeamReport(teams.get(teamIdx), new Date(currentMillis));
            }
            System.out.println( (teamIdx+1) + "/" + size + " ... DONE");
        }
    }

    public void mockWeeklyDeskReports(Date d1, Date d2, List<Desk> desks)
    {
        System.out.println("Mocking weekly desk reports");

        long startMillis = d1.getTime();
        long endMillis = d2.getTime();
        int size = desks.size();


        for (int deskIdx = 0; deskIdx< size; deskIdx++)
        {
            for(long currentMillis = startMillis; currentMillis < endMillis; currentMillis +=WEEK)
            {
                // -100 remove the first day on next week
                weeklyReportService.generateDeskReport(desks.get(deskIdx), new Date(currentMillis), new Date( currentMillis + WEEK - 100));
            }
            System.out.println( (deskIdx+1) + "/" + size + " ... DONE");
        }
    }

    public void mockWeeklyTeamReports(Date d1, Date d2, List<Team> teams)
    {
        System.out.println("Mocking weekly team reports");

        long startMillis = d1.getTime();
        long endMillis = d2.getTime();
        int size = teams.size();

        for (int teamIdx = 0; teamIdx< size; teamIdx++)
        {
            for(long time = startMillis; time < endMillis; time +=WEEK) {
                weeklyReportService.generateTeamReport(teams.get(teamIdx), new Date(time), new Date(time + WEEK - 100) );
            }
            System.out.println( (teamIdx+1) + "/" + size + " ... DONE");
        }
    }

    public void mockMonthlyDeskReports(Date d1, Date d2, List<Desk> desks)
    {
        System.out.println("Mocking monthly desk reports");

        int size = desks.size();

        for (int deskIdx = 0; deskIdx < size; deskIdx++)
        {
            monthlyReportService.generateDeskReport(desks.get(deskIdx), d1, d2);
        }
    }

    public void mockMonthlyTeamReports(Date d1, Date d2, List<Team> teams)
    {
        System.out.println("Mocking monthly desk reports");

        int size = teams.size();

        for (int i = 0; i< size; i++)
        {
                monthlyReportService.generateTeamReport(d1, d2, teams.get(i));
        }
    }

}
