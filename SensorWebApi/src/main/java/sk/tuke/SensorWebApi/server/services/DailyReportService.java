package sk.tuke.SensorWebApi.server.services;

import com.github.rkumsher.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.DailyReport;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.entities.Report;
import sk.tuke.SensorWebApi.server.repositories.DailyReportRepository;
import sk.tuke.SensorWebApi.server.repositories.ReportRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class DailyReportService
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private final Logger logger = LoggerFactory.getLogger(DailyReportService.class);
    private final float REPORTS_PER_DAY = 48.00f;


    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ReportRepository reportRepository;

    public DailyReportService() { }


    /**
     * maybe use actual time instead end of the day ?
     */

    public void generateReport(Desk desk, Date day)
    {
        List<Report> reports = reportRepository.findAllByTimestampBetweenAndDesk(
                DateUtils.atStartOfDay(day), DateUtils.atEndOfDay(day), desk);

        if (reports.isEmpty()) {
            logger.warn("Cant' find any reports per day " + DATE_FORMAT.format(day));
            return;
        }

        float averageOccupation = getAverageOccupation(reports);
        dailyReportRepository.save(new DailyReport(desk, averageOccupation));
    }

    private float getAverageOccupation(List<Report> reports) {
        float occupiedIntervals = 0;

        for (Report report: reports) {
            if(report.isOccupied()) {
                ++occupiedIntervals;
            }
        }
        return (occupiedIntervals / REPORTS_PER_DAY);
    }
}
