package sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Team;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.MonthlyReport;

import java.util.List;

public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {
    MonthlyReport findByDesk(Desk desk);
    List<MonthlyReport> findAllByTeam(Team team);
}
