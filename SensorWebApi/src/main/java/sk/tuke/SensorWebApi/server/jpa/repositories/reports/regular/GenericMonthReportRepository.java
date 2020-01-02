package sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.GenericMonthReport;

public interface GenericMonthReportRepository extends JpaRepository<GenericMonthReport, Long> {
}
