package sk.tuke.SensorWebApi.server.repositories;

/**
 * Created by IntelliJ IDEA.
 *
 * @project SensorWebApi
 * @author jabu
 * @date 10/31/19
 */

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Desk;

public interface DeskRepository extends JpaRepository<Desk, Long> { }
