package sk.tuke.SensorWebApi.server.request;

public class NewDesk {

    private String teamName;
    private String officeName;

    public NewDesk() { }

    public boolean isValid() {
        return (teamName != null && officeName != null);
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
