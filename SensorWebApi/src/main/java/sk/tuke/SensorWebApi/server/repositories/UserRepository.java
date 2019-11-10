package sk.tuke.SensorWebApi.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.SensorWebApi.server.entities.Persona;

public interface UserRepository extends JpaRepository<Persona, Long> {
    Persona findByUsername(String username);
}
