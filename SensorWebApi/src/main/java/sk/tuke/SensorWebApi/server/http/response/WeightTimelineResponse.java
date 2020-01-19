package sk.tuke.SensorWebApi.server.http.response;

import java.util.ArrayList;
import java.util.List;

public class WeightTimelineResponse
{
    private Long Id;
    private String day;
    private int[] weights;
    private List<String> timelineColors = new ArrayList<>();
    private final String[] colors = {"#b3ff66", "#a6ff4d", "#8cff1a" , "#80ff00" , "#73e600" ,"#66cc00" ,"#59b300" ,"#4d9900" ,"#336600" ,"#264d00"};


    public WeightTimelineResponse(Long id, String day, String weights, int maxWeight) {
        Id = id;
        this.day = day;
        setWeights(weights);
        setColors(maxWeight);
    }

    private void setColors(int maxW) {
        for(int i =0; i< 48; i++) {
            timelineColors.add(colors[((weights[i] * 100) / maxW) % 10]);
        }
    }

    private void setWeights(String weights) {
        this.weights = new int[48];
        String[] strWeights = weights.split(";");

        for(int i =0; i< 48; i++) {
            this.weights[i] = Integer.parseInt(strWeights[i]);
        }
    }

    public void setWeights(int[] weights) {
        this.weights = weights;
    }

    public List<String> getTimelineColors() {
        return timelineColors;
    }

    public void setTimelineColors(List<String> timelineColors) {
        this.timelineColors = timelineColors;
    }

    public String[] getColors() {
        return colors;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int[] getWeights() {
        return weights;
    }

    public void setTimeline(int[] timeline) {
        this.weights = timeline;
    }
}
