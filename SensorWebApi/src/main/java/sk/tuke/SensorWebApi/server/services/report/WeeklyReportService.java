package sk.tuke.SensorWebApi.server.services.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.DailyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.WeeklyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.DailyTeamReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.WeeklyTeamReportRepository;

import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

@Service
public class WeeklyReportService
{
    private final Logger logger = LoggerFactory.getLogger(WeeklyReportService.class);
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private final float WORKING_DAYS = 5.00f;

    @Autowired private DailyReportRepository dailyReportRepository;
    @Autowired private WeeklyReportRepository weeklyReportRepository;
    @Autowired private DailyTeamReportRepository dailyTeamReportRepository;
    @Autowired private WeeklyTeamReportRepository weeklyTeamReportRepository;

    public void generateDeskReport(Desk desk, Date startWeek, Date endWeek) {
        List<DailyReport> dailyReports = dailyReportRepository.findAllByDayBetweenAndDesk(startWeek, endWeek, desk);

        if (dailyReports.isEmpty()) {
            logger.error("DAILY DESK REPORT");
            logger.error("Cant' find any reports per week " + DATE_FORMAT.format(startWeek) + " - " + DATE_FORMAT.format(endWeek));
            logger.error("Canceled for desk " + desk.toString());
            return;
        }

        float averageOccupation = countAverageOccupation(dailyReports);
        WeeklyReport weeklyReport = new WeeklyReport(averageOccupation, desk, startWeek, dailyReports);
        weeklyReportRepository.save(weeklyReport);

        logger.info(" ---- CREATED ----");
        logger.info(weeklyReport.toString());

        try {
            dailyReports.forEach( dailyReport -> {
                dailyReport.setWeeklyReport(weeklyReport);
                dailyReportRepository.save(dailyReport);
            });
        } catch (ConcurrentModificationException e) {
            System.out.println("just another smile (:");
        }
    }

    public void generateTeamReport(Team team, Date startWeek, Date endWeek) {
        List<DailyTeamReport> dailyTeamReports = dailyTeamReportRepository.findAllByDayBetweenAndTeam(startWeek, endWeek, team);

        if (dailyTeamReports.isEmpty()) {
            logger.error("DAILY TEAM REPORT");
            logger.error("Cant' find any reports per week " + DATE_FORMAT.format(startWeek) + " - " + DATE_FORMAT.format(endWeek));
            logger.error("Canceled for team " + team.toString());
            return;
        }

        float averageOccupation = getOccupation(dailyTeamReports);

        sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport weeklyTeamReport = new sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport(startWeek, averageOccupation, team, dailyTeamReports);
        weeklyTeamReportRepository.save(weeklyTeamReport);

        logger.info(" ---- CREATED ----");
        logger.info(weeklyTeamReport.toString());

        try {
            dailyTeamReports.forEach( dailyTeamReport -> {
                dailyTeamReport.setWeeklyTeamReport(weeklyTeamReport);
                dailyTeamReportRepository.save(dailyTeamReport);
            });
        } catch (ConcurrentModificationException e) {
            System.out.println("just another smile (:");
        }


    }

    private float getOccupation(List<DailyTeamReport> reports) {
        float sum = 0.00f;

        for (DailyTeamReport dailyTeamReport : reports) sum += dailyTeamReport.getAverageOccupation();

        return sum / WORKING_DAYS;
    }

    private float countAverageOccupation(List<DailyReport> reports) {
        float sum = 0.00f;

        for (DailyReport dailyReport : reports) sum += dailyReport.getAverageOccupation();

        return sum / WORKING_DAYS;
    }

}
