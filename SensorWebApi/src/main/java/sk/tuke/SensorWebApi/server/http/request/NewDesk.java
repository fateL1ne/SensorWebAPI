package sk.tuke.SensorWebApi.server.http.request;

public class NewDesk {

    private String deskLabel;
    private String teamName;
    private String officeName;

    public NewDesk() { }

    public boolean isValid() {
        return (deskLabel != null && teamName != null && officeName != null);
    }

    public String getDeskLabel() {
        return deskLabel;
    }

    public void setDeskLabel(String deskLabel) {
        this.deskLabel = deskLabel;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
