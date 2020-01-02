package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Desk;
import sk.tuke.SensorWebApi.server.entities.MonthlyReport;

public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {
    MonthlyReport findByDesk(Desk desk);
}
