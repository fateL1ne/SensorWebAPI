package sk.tuke.SensorWebApi.server.jpa.repositories.models;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.WeightTimeline;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.team.MonthlyTeamAttendance;

import java.util.List;

public interface WeightTimelineRepository extends JpaRepository<WeightTimeline, Long> {
    List<WeightTimeline> getAllByMonthlyTeamAttendance(MonthlyTeamAttendance monthlyTeamAttendance);
}
