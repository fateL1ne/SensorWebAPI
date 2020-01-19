package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.core.WeightTimeline;

import java.util.ArrayList;
import java.util.List;

public class MonthlyTeamStatsResponse
{
    private Long Id;
    private String teamName;
    private int teamDesks;
    private String teamLeader;
    private List<WeightTimelineResponse> weightTimelineResponseList = new ArrayList<>();


    public MonthlyTeamStatsResponse(Long id, Team team, List<WeightTimeline> weightTimelines) {
        Id = id;
        this.teamName = team.getTeamName();
        this.teamDesks = team.getDesks().size();
        this.teamLeader = team.getLeader();
        weightTimelines.forEach( weightTimeline -> {
            this.weightTimelineResponseList.add(new WeightTimelineResponse(
                    weightTimeline.getId(),
                    weightTimeline.getDay(),
                    weightTimeline.getWeights(),
                    teamDesks * 4));
        });
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamDesks() {
        return teamDesks;
    }

    public void setTeamDesks(int teamDesks) {
        this.teamDesks = teamDesks;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public List<WeightTimelineResponse> getWeightTimelineResponseList() {
        return weightTimelineResponseList;
    }

    public void setWeightTimelineResponseList(List<WeightTimelineResponse> weightTimelineResponseList) {
        this.weightTimelineResponseList = weightTimelineResponseList;
    }
}
