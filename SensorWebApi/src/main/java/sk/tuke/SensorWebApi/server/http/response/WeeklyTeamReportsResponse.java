package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport;

import java.util.ArrayList;
import java.util.List;

public class WeeklyTeamReportsResponse {

    private List<WeeklyTeamReportResponse> weeklyTeamReportResponses = new ArrayList<>();

    public WeeklyTeamReportsResponse(List<WeeklyTeamReport> weeklyTeamReports)
    {
        weeklyTeamReports.forEach( weeklyTeamReport ->
            weeklyTeamReportResponses.add(new WeeklyTeamReportResponse(weeklyTeamReport))
        );
    }

    public List<WeeklyTeamReportResponse> getWeeklyTeamReportResponses() {
        return weeklyTeamReportResponses;
    }

    public void setWeeklyTeamReportResponses(List<WeeklyTeamReportResponse> weeklyTeamReportResponses) {
        this.weeklyTeamReportResponses = weeklyTeamReportResponses;
    }

    public class WeeklyTeamReportResponse
    {

        private long weekReportID;
        private float averageOccupation;
        private String teamName;


        public WeeklyTeamReportResponse(WeeklyTeamReport weeklyTeamReport) {
            this.weekReportID = weeklyTeamReport.getId();
            this.averageOccupation = weeklyTeamReport.getAverageOccupation();
            this.teamName = weeklyTeamReport.getTeam().getTeamName();
        }

        public long getWeekReportID() {
            return weekReportID;
        }

        public void setWeekReportID(long weekReportID) {
            this.weekReportID = weekReportID;
        }

        public float getAverageOccupation() {
            return averageOccupation;
        }

        public void setAverageOccupation(float averageOccupation) {
            this.averageOccupation = averageOccupation;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
    }
}
