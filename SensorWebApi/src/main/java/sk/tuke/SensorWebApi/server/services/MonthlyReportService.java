package sk.tuke.SensorWebApi.server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.SensorWebApi.server.entities.*;

import sk.tuke.SensorWebApi.server.repositories.MonthlyReportRepository;
import sk.tuke.SensorWebApi.server.repositories.MonthlyTeamReportRepository;
import sk.tuke.SensorWebApi.server.repositories.TeamRepository;
import sk.tuke.SensorWebApi.server.repositories.WeeklyReportRepository;

import java.util.Date;
import java.util.List;


@Service
public class MonthlyReportService
{

    @Autowired private MonthlyReportRepository monthlyReportRepository;
    @Autowired private WeeklyReportRepository weeklyReportRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private MonthlyTeamReportRepository teamReportRepository;

    public void generateGenericReport() {

    }

    public void generateTeamReport(Date month)
    {
        List<Team> teams = teamRepository.findAll();

        teams.forEach( team ->
        {
            List<Desk> desks = team.getDesks();
            float occupation = 0.00f;

            for (Desk desk : desks) {
                occupation += monthlyReportRepository.findByDesk(desk).getAverageOccupation();
            }

            occupation /= desks.size();

           teamReportRepository.save(new MonthlyTeamReport(team, occupation, month));
        });
    }

    public void generateDeskReport(Desk desk, Date startMonth, Date endMonth)
    {
        List<WeeklyReport> weeklyReports = weeklyReportRepository
                .findAllByWeekBetweenAndDesk(startMonth, endMonth, desk);
        float averageOccupation = 0.00f;

        for (WeeklyReport weeklyReport : weeklyReports)
            averageOccupation += weeklyReport.getAverageOccupation();

        averageOccupation /= weeklyReports.size();

        MonthlyReport monthlyReport = new MonthlyReport(averageOccupation, weeklyReports, desk, startMonth);

        weeklyReports.forEach( weeklyReport -> {
            weeklyReport.setMonthlyReport(monthlyReport);
            weeklyReportRepository.save(weeklyReport);
        });

        monthlyReportRepository.save(monthlyReport);
    }

}
