package sk.tuke.SensorWebApi.server.controllers.response;

import sk.tuke.SensorWebApi.server.entities.WeeklyReport;

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
