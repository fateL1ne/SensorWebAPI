package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;

import java.util.ArrayList;
import java.util.List;

public class DailyReportsResponse
{

    private List<DailyReportResponse>
            dailyReportResponses = new ArrayList<>();

    public DailyReportsResponse(List<DailyReport> dailyReports) {
        dailyReports.forEach( dailyReport -> this.dailyReportResponses.add( new DailyReportResponse(
                dailyReport.getId(),
                dailyReport.getDesk().getId(),
                dailyReport.getAverageOccupation(),
                dailyReport.getDay(),
                dailyReport.getTimeline()
        )));
    }

    public List<DailyReportResponse> getDailyReportResponses() {
        return dailyReportResponses;
    }

    public void setDailyReportResponses(List<DailyReportResponse> dailyReportResponses) {
        this.dailyReportResponses = dailyReportResponses;
    }
}
