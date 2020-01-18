package sk.tuke.SensorWebApi.server.services.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.http.response.DailyReportsResponse;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.DailyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.ReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.DailyTeamReportRepository;
import sk.tuke.SensorWebApi.server.services.suggestion.desks.TimelineService;

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


    @Autowired private DailyReportRepository dailyReportRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private TimelineService timelineService;
    @Autowired private DailyTeamReportRepository dailyTeamReportRepository;


    public DailyReportService() { }

    public void generateDeskReport(Desk desk, Date day) {
        List<Report> reports = reportRepository.findAllByTimestampBetweenAndDesk(day, new Date(), desk);

        if (reports.isEmpty()) {
            logger.warn("Cant' find any reports per day " + DATE_FORMAT.format(day));
            logger.warn("Generating report per desk " + desk.toString() + " canceled");
            return;
        }

        float averageOccupation = getAverageOccupation(reports);
        long timeline = timelineService.generateTimeline(reports);

        DailyReport newReport = new DailyReport(desk, averageOccupation, day, timeline);
        dailyReportRepository.save(newReport);

        logger.info("--------- CREATED ------------");
        logger.info(newReport.toString());
    }


    public DailyReportsResponse getAllByDayAndTeamId(Date day, long teamId) {
        return new DailyReportsResponse(dailyReportRepository.findAllByDayAndDeskTeamId(day, teamId));
    }

    public void generateTeamReport(Team team, Date day)
    {
        float occupation = 0.00f;
        List<DailyReport> dailyReports = dailyReportRepository.findAllByDayAndDesk_Team(day, team);

        if(dailyReports.isEmpty()) {
            logger.error("Can't find any daily reports for team " + team.getTeamName() + "at day " + DATE_FORMAT.format(day));
            logger.error("Generating team report canceled");
            return;
        }

        for ( DailyReport dailyReport : dailyReports ) occupation += dailyReport.getAverageOccupation();
        occupation /= dailyReports.size();

        DailyTeamReport dailyTeamReport = new DailyTeamReport(team, day, occupation);
        dailyTeamReportRepository.save(dailyTeamReport);

        logger.info(" ---- CREATED ----");
        logger.info(dailyTeamReport.toString());
    }

    private float getAverageOccupation(List<Report> reports) {
        float occupiedIntervals = 0.00f;

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
