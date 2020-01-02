package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.MonthlyTeamReport;

import java.util.Date;
import java.util.List;

public interface MonthlyTeamReportRepository extends JpaRepository<MonthlyTeamReport, Long> {
    List<MonthlyTeamReport> findAllByMonth(Date date);
}
