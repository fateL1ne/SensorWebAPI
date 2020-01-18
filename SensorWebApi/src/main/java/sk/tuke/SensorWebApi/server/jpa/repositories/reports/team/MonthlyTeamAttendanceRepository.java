package sk.tuke.SensorWebApi.server.jpa.repositories.reports.team;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamAttendance;

import java.util.Date;
import java.util.List;

public interface MonthlyTeamAttendanceRepository extends JpaRepository<MonthlyTeamAttendance, Long> {
    List<MonthlyTeamAttendance> getAllByMonth(Date month);
}
