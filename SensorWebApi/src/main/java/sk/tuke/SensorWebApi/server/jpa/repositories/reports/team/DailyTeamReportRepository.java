package sk.tuke.SensorWebApi.server.jpa.repositories.reports.team;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.DailyTeamReport;

import java.util.Date;
import java.util.List;

public interface DailyTeamReportRepository extends JpaRepository<DailyTeamReport, Long> {
    List<DailyTeamReport> findAllByDay(Date date);
    List<DailyTeamReport> findAllByDayBetweenAndTeam(Date d1, Date d2, Team team);
}
