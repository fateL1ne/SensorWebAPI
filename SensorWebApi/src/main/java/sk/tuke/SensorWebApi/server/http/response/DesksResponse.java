package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DesksResponse
{

    private final List<DeskResponse> desks = new ArrayList<>();

    public DesksResponse(List<Desk> desks) { desks.forEach(desk -> this.desks.add(new DeskResponse(desk))); }

    public List<DeskResponse> getDesks() { return desks; }


}
