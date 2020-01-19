package sk.tuke.SensorWebApi.server.services.suggestion.desks;

import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.Report;

import java.util.List;

@Service
public class TimelineService
{
    private static final int BIT_SHIFT = 0x01;

    public long generateTimeline(List<Report> reports)
    {
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

    long getAverageDailyTimeline(List<DailyReport> dailyReports)
    {
        int size = dailyReports.size() / 2;
        long averageTimeline = 0;
        long timeline;

        int[] weightTimeline = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, };

        for(DailyReport dailyReport : dailyReports) {
            timeline = dailyReport.getTimeline();

            for(int i=0; i < 48; i++) {
                if( (timeline &(1L << i)) != 0) {
                    weightTimeline[i]++;
                }
            }
        }

        for(int i=0; i < 48; i++) {
            if(weightTimeline[i] > size)
                averageTimeline |= 1L << i;
        }

        return averageTimeline;
    }


    int gaps(long t1, long t2) {
        return Long.numberOfLeadingZeros(t1 ^ t2);
    }

}
