package sk.tuke.SensorWebApi.server.services.suggestion.desks;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

public class GraphVertex
{
    private Desk desk;
    private long averageTimeline;

    GraphVertex(Desk desk, long averageTimeline)
    {
        this.desk = desk;
        this.averageTimeline = averageTimeline;
    }

    public Desk getDesk() { return desk; }

    public void setDesk(Desk desk) { this.desk = desk; }

    long getAverageTimeline() { return averageTimeline; }

    public void setAverageTimeline(long averageTimeline) { this.averageTimeline = averageTimeline; }
}
