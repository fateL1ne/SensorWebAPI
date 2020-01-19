package sk.tuke.SensorWebApi.server.services.suggestion.teams;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.core.WeightTimeline;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamAttendance;
import sk.tuke.SensorWebApi.server.jpa.repositories.models.WeightTimelineRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular.DailyReportRepository;
import sk.tuke.SensorWebApi.server.jpa.repositories.reports.team.MonthlyTeamAttendanceRepository;
import sk.tuke.SensorWebApi.server.services.helpers.DateService;
import sk.tuke.SensorWebApi.server.services.suggestion.desks.Day;

import java.util.*;


@Service
public class TeamStatsService
{
    @Autowired private DailyReportRepository dailyReportRepository;
    @Autowired private WeightTimelineRepository weightTimelineRepository;
    @Autowired private MonthlyTeamAttendanceRepository monthlyTeamAttendanceRepository;
    @Autowired private DateService dateService;

    private final Day[] days = { Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY };
    private final int DAY_INTERVALS = 48;
    private final long DAY = 24 * 60 * 60 * 1000;
    private final long WEEK = DAY * 7;
    private final int TOTAL_DAYS = 7-2;


    public void generateTeamStats(Date startOfMonth, Date endOfMonth, Team team)
    {
        MonthlyTeamAttendance monthlyTeamAttendance = new MonthlyTeamAttendance(team, startOfMonth);
        monthlyTeamAttendanceRepository.save(monthlyTeamAttendance);

        for (int day = 0; day < TOTAL_DAYS; day++)
        {
            Date dayDate = dateService.getFirstDayDate(startOfMonth,day + 2);
            List<DailyReport> dailyReports = getAllTeamReportsPerDay(dayDate, endOfMonth, team);
            int[] timeline = getAverageTeamTimeline(dailyReports);
            WeightTimeline weightTimeline = new WeightTimeline(monthlyTeamAttendance, days[day].getName(), weightsToString(timeline));
            weightTimelineRepository.save(weightTimeline);
        }

    }


    private List<DailyReport> getAllTeamReportsPerDay(Date day, Date endOfMonth, Team team)
    {
        List<DailyReport> dailyReports = new ArrayList<>();
        long dayMillis = day.getTime();
        long endOfMonthMillis = endOfMonth.getTime();

        for (long currentMillis = dayMillis; currentMillis < endOfMonthMillis; currentMillis+=WEEK)
        {
            dailyReports.addAll(dailyReportRepository.findAllByDayAndDesk_Team(new Date(currentMillis), team));
        }

        return dailyReports;
    }

    private String weightsToString(int[] weights) {
        String str = "";

        for (int i=0; i < DAY_INTERVALS; i++)
            str += weights[i] + ";";

        return str;
    }



    private int[] getAverageTeamTimeline(List<DailyReport> dailyReports)
    {
        int[] weightTimeline = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, };

        long currentTimeline;

        for (DailyReport dailyReport : dailyReports)
        {
            currentTimeline = dailyReport.getTimeline();

            for (int day_interval = 0; day_interval < DAY_INTERVALS; day_interval++)
            {
                if ( (currentTimeline & (1L << day_interval)) != 0)
                {
                    weightTimeline[day_interval]++;
                }
            }
        }

        return weightTimeline;
    }

}
