package sk.tuke.SensorWebApi.server.services.report;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.MonthlyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.MonthlyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.WeeklyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.MonthlyTeamReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.WeeklyTeamReportRepository;

import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;


@Service
public class MonthlyReportService
{

    @Autowired private MonthlyReportRepository monthlyReportRepository;
    @Autowired private WeeklyReportRepository weeklyReportRepository;
    @Autowired private MonthlyTeamReportRepository monthlyTeamReportRepository;
    @Autowired private WeeklyTeamReportRepository weeklyTeamReportRepository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    public void generateTeamReport(Date startMonth, Date endMonth, Team team)
    {
        List<WeeklyTeamReport> weeklyTeamReports = weeklyTeamReportRepository.findAllByWeekBetweenAndTeam(startMonth, endMonth, team);

        if (weeklyTeamReports.isEmpty()) {
            logger.error("MONTHLY TEAM REPORT");
            logger.error("Can't find any weekly report per: " + DATE_FORMAT.format(startMonth) + " - " + DATE_FORMAT.format(endMonth));
            logger.error("Generating monthly report canceled");
            return;
        }

        float averageOccupation = 0.00f;

        for (WeeklyTeamReport weeklyTeamReport : weeklyTeamReports) averageOccupation += weeklyTeamReport.getAverageOccupation();
        averageOccupation /= weeklyTeamReports.size();

        MonthlyTeamReport monthlyTeamReport = new MonthlyTeamReport(team, averageOccupation, startMonth, weeklyTeamReports);
        monthlyTeamReportRepository.save(monthlyTeamReport);

        try {
            weeklyTeamReports.forEach(weeklyReport -> {
                weeklyReport.setMonthlyTeamReport(monthlyTeamReport);
                weeklyTeamReportRepository.save(weeklyReport);
            });
        } catch (ConcurrentModificationException e) {
            System.out.println("(:");
        }

    }

    public void generateDeskReport(Desk desk, Date startMonth, Date endMonth)
    {
        List<WeeklyReport> weeklyReports = weeklyReportRepository.findAllByWeekBetweenAndDesk(startMonth, endMonth, desk);

        if (weeklyReports.isEmpty()) {
            logger.error("MONTHLY DESK REPORT");
            logger.error("Can't find any weekly report per: " + DATE_FORMAT.format(startMonth) + " - " + DATE_FORMAT.format(endMonth));
            logger.error("Generating monthly report canceled");
            return;
        }

        float averageOccupation = 0.00f;

        for (WeeklyReport weeklyReport : weeklyReports)
            averageOccupation += weeklyReport.getAverageOccupation();

        averageOccupation /= weeklyReports.size();

        MonthlyReport monthlyReport = new MonthlyReport(averageOccupation, weeklyReports, desk, startMonth, desk.getTeam());
        monthlyReportRepository.save(monthlyReport);

        try {
            weeklyReports.forEach(weeklyReport -> {
                weeklyReport.setMonthlyReport(monthlyReport);
                weeklyReportRepository.save(weeklyReport);
            });
        } catch (ConcurrentModificationException e) {
            System.out.println("(:");
        }
    }


}
