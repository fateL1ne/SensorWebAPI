package sk.tuke.SensorWebApi.server.http.response;


import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.DailyTeamReport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailyTeamReportsResponse implements Serializable {

    private List<DailyTeamReportResponse> dailyTeamReportResponses = new ArrayList<>();

    public DailyTeamReportsResponse(List<DailyTeamReport> dailyTeamReports) {
        dailyTeamReports.forEach(dailyTeamReport -> dailyTeamReportResponses.add(new DailyTeamReportResponse(
                dailyTeamReport.getTeam().getId(),
                dailyTeamReport.getTeam().getTeamName(),
                dailyTeamReport.getAverageOccupation()
        )));
    }

    public List<DailyTeamReportResponse> getDailyTeamReportResponses() {
        return dailyTeamReportResponses;
    }

    public void setDailyTeamReportResponses(List<DailyTeamReportResponse> dailyTeamReportResponses) {
        this.dailyTeamReportResponses = dailyTeamReportResponses;
    }


    public class DailyTeamReportResponse implements Serializable {
        private Long teamId;
        private String teamName;
        private float averageOccupation;

        public DailyTeamReportResponse(Long teamId, String teamName, float averageOccupation) {
            this.teamId = teamId;
            this.teamName = teamName;
            this.averageOccupation = averageOccupation;
        }

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public float getAverageOccupation() {
            return averageOccupation;
        }

        public void setAverageOccupation(float averageOccupation) {
            this.averageOccupation = averageOccupation;
        }
    }
}
