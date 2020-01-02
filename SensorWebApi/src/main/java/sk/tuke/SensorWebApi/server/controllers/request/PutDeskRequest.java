package sk.tuke.SensorWebApi.server.controllers.request;

public class PutDeskRequest {

    private String newTeam;
    private String newOffice;
    private Long deskId;

    public PutDeskRequest() { }

    public boolean isValid() { return (deskId != null && newOffice != null && newTeam != null); }

    public String getNewTeam() {
        return newTeam;
    }

    public void setNewTeam(String newTeam) {
        this.newTeam = newTeam;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public String getNewOffice() {
        return newOffice;
    }

    public void setNewOffice(String newOffice) {
        this.newOffice = newOffice;
    }
}
