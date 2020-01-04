package sk.tuke.SensorWebApi.server.jpa.repositories.reports.team;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamReport;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport;

import java.util.Date;
import java.util.List;

public interface MonthlyTeamReportRepository extends JpaRepository<MonthlyTeamReport, Long> {
    List<MonthlyTeamReport> findAllByMonth(Date date);
}
