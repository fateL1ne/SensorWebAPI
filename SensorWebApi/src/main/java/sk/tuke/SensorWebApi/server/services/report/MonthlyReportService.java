package sk.tuke.SensorWebApi.server.services.report;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.GenericMonthReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.MonthlyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.*;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.GenericMonthReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.MonthlyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.WeeklyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.MonthlyTeamReportRepository;

import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;


@Service
public class MonthlyReportService
{

    @Autowired private MonthlyReportRepository monthlyReportRepository;
    @Autowired private WeeklyReportRepository weeklyReportRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private MonthlyTeamReportRepository teamReportRepository;
    @Autowired private GenericMonthReportRepository genericMonthReportRepository;
    @Autowired private DeskRepository deskRepository;
    @Autowired private OfficeRepository officeRepository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");


    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    public void generateGenericReport(Date month)
    {

        List<MonthlyTeamReport> list = teamReportRepository.findAll();

        float occupation = 0.00f;

        for (MonthlyTeamReport monthlyTeamReport : list)
            occupation += monthlyTeamReport.getAverageOccupation();

        occupation /= list.size();

        teamReportRepository.findAll();

        genericMonthReportRepository.save(
                new GenericMonthReport(
                        deskRepository.findAll().size(),
                        teamRepository.findAll().size(),
                        officeRepository.findAll().size(),
                        occupation
                )
        );
    }

    public void generateTeamReport(Date month)
    {
        List<Team> teams = teamRepository.findAll();

        teams.forEach( team ->
        {
            List<Desk> desks = team.getDesks();
            float occupation = 0.00f;

            for (Desk desk : desks) {
                occupation += monthlyReportRepository.findByDesk(desk).getAverageOccupation();
            }

            occupation /= desks.size();

           teamReportRepository.save(new MonthlyTeamReport(team, occupation, month));
        });
    }

    public void generateDeskReport(Desk desk, Date startMonth, Date endMonth)
    {
        List<WeeklyReport> weeklyReports = weeklyReportRepository
                .findAllByWeekBetweenAndDesk(startMonth, endMonth, desk);

        if (weeklyReports.isEmpty()) {
            logger.warn("Can't find any weekly report per: " + DATE_FORMAT.format(startMonth) + " - " + DATE_FORMAT.format(endMonth));
            logger.warn("Generating monthly report canceled");
            return;
        }

        float averageOccupation = 0.00f;

        for (WeeklyReport weeklyReport : weeklyReports)
            averageOccupation += weeklyReport.getAverageOccupation();

        averageOccupation /= weeklyReports.size();

        MonthlyReport monthlyReport = new MonthlyReport(averageOccupation, weeklyReports, desk, startMonth, desk.getTeam());
        monthlyReportRepository.save(monthlyReport);

        try {
            weeklyReports.forEach( weeklyReport -> {
                weeklyReport.setMonthlyReport(monthlyReport);
                weeklyReportRepository.save(weeklyReport);
            });
        } catch (ConcurrentModificationException e) {
            System.out.println("(:");
        }
    }


}
