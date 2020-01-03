package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import java.io.Serializable;

public class DeskResponse implements Serializable {

    private Long id;
    private String label;
    private String officeName;
    private String teamName;

    public DeskResponse(Desk desk) {
        this.id = desk.getId();
        this.label = desk.getLabel();
        this.officeName = desk.getOffice().getOfficeName();
        this.teamName = desk.getTeam().getTeamName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}


