package sk.tuke.SensorWebApi.server.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.jpa.entities.core.Persona;

public interface UserRepository extends JpaRepository<Persona, Long> {
    Persona findByUsername(String username);
}
