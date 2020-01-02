package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;

public class WeeklyTeamReportsResponse {

    public class WeeklyTeamReportResponse {
        private long weekReportID;
        private float averageOccupation;

        public WeeklyTeamReportResponse(WeeklyReport weeklyReport) {
            this.weekReportID = weeklyReport.getId();
            this.averageOccupation = weeklyReport.getAverageOccupation();

        }
    }
}
