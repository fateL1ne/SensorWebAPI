package sk.tuke.SensorWebApi.server.services.report;

import com.github.rkumsher.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.ReportRepository;
import sk.tuke.SensorWebApi.server.services.core.TimelineService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class DailyReportService
{
    private final Logger logger = LoggerFactory.getLogger(DailyReportService.class);

    private static final int REPORTS_PER_DAY = 48;
    private static final SimpleDateFormat INTERVAL_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd MMMMM EEEEE");
    private static final long HALF_HOUR = 30 * 60 * 1000;
    private static final String SEPARATOR = "----------------------------------------------";
    private final String NEW_LINE = System.lineSeparator();


    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TimelineService timelineService;

    public DailyReportService() { }

    public void generateReport(Desk desk, Date day) {
        List<Report> reports = reportRepository.findAllByTimestampBetweenAndDesk(
                DateUtils.atStartOfDay(day), new Date(System.currentTimeMillis()), desk);

        if (reports.isEmpty()) {
            logger.warn("Cant' find any reports per day " + DATE_FORMAT.format(day));
            return;
        }

        float averageOccupation = getAverageOccupation(reports);
        long timeline = timelineService.generateTimeline(reports);
        DailyReport newReport = new DailyReport(desk, averageOccupation, DateUtils.atStartOfDay(day), timeline);

        dailyReportRepository.save(newReport);
        logger.info(stringify(newReport));
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

    private String stringify(DailyReport dailyReport) {
        long bit_mask = 1;
        long timeline = dailyReport.getTimeline();
        Date day = dailyReport.getDay();
        Desk desk = dailyReport.getDesk();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(NEW_LINE).append(SEPARATOR).append(NEW_LINE);
        stringBuilder
                .append("Desk: ")
                .append(desk.getId()).append(", Team: ")
                .append(desk.getTeam().getTeamName())
                .append(NEW_LINE);
        stringBuilder.append(DATE_FORMAT.format(day)).append(NEW_LINE);
        stringBuilder.append(SEPARATOR).append(NEW_LINE).append(NEW_LINE);

        for (int interval = 0; interval < REPORTS_PER_DAY; interval++) {
            if ((bit_mask & timeline) != 0) {
                stringBuilder.append(getInterval(day, interval)).append(" ---> Occupied").append(NEW_LINE);
            } else {
                stringBuilder.append(getInterval(day, interval)).append(" ---> Free").append(NEW_LINE);
            }
            bit_mask <<= 0x01;
        }

        stringBuilder.append(SEPARATOR).append(NEW_LINE);

        return stringBuilder.toString();
    }

    private static String getInterval(Date day, int interval) {
        if (interval == 0) {
            return (
                    INTERVAL_FORMAT.format(day.toInstant().toEpochMilli())
                            + " - " + INTERVAL_FORMAT.format(day.toInstant().toEpochMilli() + HALF_HOUR)
            );
        }
        return (
                INTERVAL_FORMAT.format(day.toInstant().toEpochMilli() + HALF_HOUR * interval)
                        + " - " + INTERVAL_FORMAT.format(day.toInstant().toEpochMilli() + HALF_HOUR * (interval + 1))
        );
    }
}
