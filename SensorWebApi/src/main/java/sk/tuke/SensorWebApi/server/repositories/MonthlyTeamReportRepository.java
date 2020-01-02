package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.MonthlyTeamReport;

public interface MonthlyTeamReportRepository extends JpaRepository<MonthlyTeamReport, Long> {
}
