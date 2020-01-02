package sk.tuke.SensorWebApi.server.controllers.response;

import sk.tuke.SensorWebApi.server.entities.MonthlyTeamReport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MonthlyTeamReportsResponse implements Serializable {

    private List<MonthlyTeamReportResponse> monthlyTeamReportResponses = new ArrayList<>();

    public MonthlyTeamReportsResponse(List<MonthlyTeamReport> monthlyTeamReports) {
        monthlyTeamReports.forEach(monthlyTeamReport -> monthlyTeamReportResponses.add(new MonthlyTeamReportResponse(monthlyTeamReport)));
    }

    public List<MonthlyTeamReportResponse> getMonthlyTeamReportResponses() {
        return monthlyTeamReportResponses;
    }

    public void setMonthlyTeamReportResponses(List<MonthlyTeamReportResponse> monthlyTeamReportResponses) {
        this.monthlyTeamReportResponses = monthlyTeamReportResponses;
    }

    public class MonthlyTeamReportResponse implements Serializable {
        private long monthlyReportId;
        private long teamID;
        private Float averageOccupation;
        private String teamName;
        private int numberOfDesks;

        public MonthlyTeamReportResponse(MonthlyTeamReport monthlyTeamReport) {
            this.monthlyReportId = monthlyTeamReport.getId();
            this.teamID = monthlyTeamReport.getTeam().getId();
            this.averageOccupation = monthlyTeamReport.getAverageOccupation();
            this.teamName = monthlyTeamReport.getTeam().getTeamName();
            this.numberOfDesks = monthlyTeamReport.getTeam().getDesks().size();
        }

        public Float getAverageOccupation() {
            return averageOccupation;
        }

        public void setAverageOccupation(Float averageOccupation) {
            this.averageOccupation = averageOccupation;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public int getNumberOfDesks() {
            return numberOfDesks;
        }

        public void setNumberOfDesks(int numberOfDesks) {
            this.numberOfDesks = numberOfDesks;
        }

        public long getMonthlyReportId() {
            return monthlyReportId;
        }

        public void setMonthlyReportId(long monthlyReportId) {
            this.monthlyReportId = monthlyReportId;
        }

        public long getTeamID() {
            return teamID;
        }

        public void setTeamID(long teamID) {
            this.teamID = teamID;
        }
    }
}



