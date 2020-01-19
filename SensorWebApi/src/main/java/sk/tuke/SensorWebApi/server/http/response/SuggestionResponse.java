package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import java.util.Date;

public class SuggestionResponse
{
    private String desk1Label;
    private int[] desk1Timeline;
    private String desk1Team;
    private String desk2label;
    private int[] desk2Timeline;
    private String desk2Team;
    private String day;
    private Date month;
    private String officeName;
    private int score;


    public SuggestionResponse(Desk desk1, long desk1Timeline, Desk desk2, long desk2Timeline, String day, Date month, int score)
    {
        this.desk1Label = desk1.getLabel();
        this.desk1Team = desk1.getTeam().getTeamName();
        this.desk2label = desk2.getLabel();
        this.desk2Team = desk2.getTeam().getTeamName();
        this.day = day;
        this.month = month;
        this.officeName = desk1.getOffice().getOfficeName();
        this.score = score;
    }


    private void generateTimeline(long timeline1, long timeline2) {
        desk1Timeline = new int[48];
        desk2Timeline = new int[48];

        for(int i =0; i< 48; i++)
        {
            if(((timeline1) & (1L << i)) != 0) {
                desk1Timeline[i] = 1;
            } else {
                desk1Timeline[i] = 0;
            }

            if(((timeline2) & (1L << i)) != 0) {
                desk2Timeline[i] = 1;
            } else {
                desk2Timeline[i] = 0;
            }
        }

    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getDesk1Team() {
        return desk1Team;
    }

    public void setDesk1Team(String desk1Team) {
        this.desk1Team = desk1Team;
    }

    public String getDesk2Team() {
        return desk2Team;
    }

    public void setDesk2Team(String desk2Team) {
        this.desk2Team = desk2Team;
    }

    public String getDesk1Label() {
        return desk1Label;
    }

    public void setDesk1Label(String desk1Label) {
        this.desk1Label = desk1Label;
    }



    public String getDesk2label() {
        return desk2label;
    }

    public void setDesk2label(String desk2label) {
        this.desk2label = desk2label;
    }

    public int[] getDesk1Timeline() {
        return desk1Timeline;
    }

    public void setDesk1Timeline(int[] desk1Timeline) {
        this.desk1Timeline = desk1Timeline;
    }

    public int[] getDesk2Timeline() {
        return desk2Timeline;
    }

    public void setDesk2Timeline(int[] desk2Timeline) {
        this.desk2Timeline = desk2Timeline;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
