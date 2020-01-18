package sk.tuke.SensorWebApi.server.http.response;

public class WeightTimelineResponse
{
    private Long Id;
    private String day;
    private int[] weights;


    public WeightTimelineResponse(Long id, String day, String weights) {
        Id = id;
        this.day = day;
        setWeights(weights);
    }

    private void setWeights(String weights) {
        this.weights = new int[48];
        String[] strWeights = weights.split(";");

        for(int i =0; i< 48; i++) {
            this.weights[i] = Integer.parseInt(strWeights[i]);
        }
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
