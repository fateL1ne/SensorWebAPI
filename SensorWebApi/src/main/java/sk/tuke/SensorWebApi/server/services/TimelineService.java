package sk.tuke.SensorWebApi.server.services;

import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.DailyReport;
import sk.tuke.SensorWebApi.server.entities.Report;

import java.util.List;

@Service
public class TimelineService
{
    private static final int BIT_SHIFT = 0x01;

    public long generateTimeline(List<Report> reports) {
        long timeline = 0;
        long bit_mask = 1;

        for (Report report: reports) {
            if(report.isOccupied()) {
                timeline |= bit_mask;
            }
            bit_mask <<= BIT_SHIFT;
        }
        return timeline;
    }

    /**
     * coming soon (:
     */

    public void findGaps(List<DailyReport> dailyReports) {

    }
}