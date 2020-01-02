package sk.tuke.SensorWebApi.server.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.WeeklyReport;

import java.util.Date;
import java.util.List;

public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {
    List<WeeklyReport> findAllByWeekBetweenAndDesk(Date startWeek, Date endWeek, Desk desk);
}
