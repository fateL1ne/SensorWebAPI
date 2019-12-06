package sk.tuke.SensorWebApi.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.DailyReport;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.entities.WeeklyReport;
import sk.tuke.SensorWebApi.server.repositories.DailyReportRepository;
import sk.tuke.SensorWebApi.server.repositories.WeeklyReportRepository;

import java.text.SimpleDateFormat;
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

        weeklyReportRepository.save(new WeeklyReport(dailyReports, averageOccupation, startWeek));
    }


    private float countAverageOccupation(List<DailyReport> reports) {
        float sum = 0.00f;

        for (DailyReport dailyReport : reports)
            sum += dailyReport.getAverageOccupation();

        return sum / WORKING_DAYS;
    }

}
