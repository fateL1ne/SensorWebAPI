package sk.tuke.SensorWebApi.server.jpa.repositories.reports.regular;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.regular.DailyReport;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Desk;

import java.util.Date;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    List<DailyReport> findAllByDayBetweenAndDesk(Date startWeek, Date endWeek, Desk desk);
    List<DailyReport> findAllByDay(Date day);
    List<DailyReport> findAllByDayBetween(Date startWeek, Date endWeek);

}
