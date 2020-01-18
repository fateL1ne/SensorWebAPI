package sk.tuke.SensorWebApi.server.services.suggestion.desks;

public enum Day
{
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private String DAY_NAME;

    Day(String day) { DAY_NAME = day; }

    public String getName() { return DAY_NAME; }
}
