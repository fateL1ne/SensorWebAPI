package sk.tuke.SensorWebApi.server.http.response;

public class RecentActivityResponse
{
    private int desks;
    private int teams;
    private int offices;
    private int activeDesks;

    public RecentActivityResponse(int desks, int teams, int offices, int activeDesks) {
        this.desks = desks;
        this.teams = teams;
        this.offices = offices;
        this.activeDesks = activeDesks;
    }

    public int getDesks() {
        return desks;
    }

    public void setDesks(int desks) {
        this.desks = desks;
    }

    public int getTeams() {
        return teams;
    }

    public void setTeams(int teams) {
        this.teams = teams;
    }

    public int getOffices() {
        return offices;
    }

    public void setOffices(int offices) {
        this.offices = offices;
    }

    public int getActiveDesks() {
        return activeDesks;
    }

    public void setActiveDesks(int activeDesks) {
        this.activeDesks = activeDesks;
    }
}
