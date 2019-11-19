package sk.tuke.SensorWebApi.server.request;

import java.io.Serializable;
import java.util.Date;

public class ReportRequest implements Serializable {

    private static final long serialVersionUID = 985543233254448L;

    private Long id;
    private Long desk;
    private boolean occupied;
    private Date timestamp;

    public ReportRequest() {};

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDesk() {
        return desk;
    }

    public void setDesk(Long desk) {
        this.desk = desk;
    }
}
