package sk.tuke.SensorWebApi.server.http.response;

import java.util.Date;

public class DailyReportResponse
{

    private Long id;
    private Long deskId;
    private Float averageOccupation;
    private Date day;
    private long timeline;


    public DailyReportResponse(Long id, Long deskId, Float averageOccupation, Date day, long timeline) {
        this.id = id;
        this.deskId = deskId;
        this.averageOccupation = averageOccupation;
        this.day = day;
        this.timeline = timeline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public Float getAverageOccupation() {
        return averageOccupation;
    }

    public void setAverageOccupation(Float averageOccupation) {
        this.averageOccupation = averageOccupation;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public long getTimeline() {
        return timeline;
    }

    public void setTimeline(long timeline) {
        this.timeline = timeline;
    }
}
