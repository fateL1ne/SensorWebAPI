package sk.tuke.SensorWebApi.server.request;

public class PutDeskRequest {

    private String newName;
    private Long deskId;

    public PutDeskRequest() { }


    public boolean isValid() { return (deskId != null && newName != null); }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }
}
