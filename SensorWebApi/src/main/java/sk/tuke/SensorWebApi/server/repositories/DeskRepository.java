package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Desk;

public interface DeskRepository extends JpaRepository<Desk, Long> { }
