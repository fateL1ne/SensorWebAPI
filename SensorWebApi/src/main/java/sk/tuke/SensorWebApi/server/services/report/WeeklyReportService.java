package sk.tuke.SensorWebApi.server.services.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.WeeklyReportRepository;

import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

@Service
public class WeeklyReportService
{
    private final Logger logger = LoggerFactory.getLogger(WeeklyReportService.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private static final float WORKING_DAYS = 5.00f;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private WeeklyReportRepository weeklyReportRepository;


    public void generateReport(Desk desk, Date startWeek, Date endWeek) {
        List<DailyReport> dailyReports = dailyReportRepository.findAllByDayBetweenAndDesk(startWeek, endWeek, desk);

        if (dailyReports.isEmpty()) {
            logger.warn("Cant' find any reports per week " + DATE_FORMAT.format(startWeek) + " - " + DATE_FORMAT.format(endWeek));
            return;
        }

        float averageOccupation = countAverageOccupation(dailyReports);
        WeeklyReport weeklyReport = new WeeklyReport(averageOccupation, desk, startWeek, dailyReports);

        weeklyReportRepository.save(weeklyReport);

        try {
            dailyReports.forEach( dailyReport -> {
                dailyReport.setWeeklyReport(weeklyReport);
                dailyReportRepository.save(dailyReport);
            });
        } catch (ConcurrentModificationException e) {
            System.out.println("(:");
        }

    }


    private float countAverageOccupation(List<DailyReport> reports) {
        float sum = 0.00f;

        for (DailyReport dailyReport : reports)
            sum += dailyReport.getAverageOccupation();

        return sum / WORKING_DAYS;
    }


}
