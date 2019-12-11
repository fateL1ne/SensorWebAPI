package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.DailyTeamReport;

import java.util.Date;
import java.util.List;

public interface DailyTeamReportRepository extends JpaRepository<DailyTeamReport, Long> {
    List<DailyTeamReport> findAllByDay(Date date);
}
