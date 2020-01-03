package sk.tuke.SensorWebApi.server.jpa.repositories.reports.team;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.WeeklyTeamReport;

import java.util.Date;
import java.util.List;

public interface WeeklyTeamReportRepository extends JpaRepository<WeeklyTeamReport, Long> {
    List<WeeklyTeamReport> findAllByWeekBetweenAndTeam(Date d1, Date d2, Team team);
}
