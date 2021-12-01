package za.co.discovery.interstellar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.discovery.interstellar.model.Planet;

public interface PlanetRepo extends JpaRepository<Planet, Long> {

    Planet findByPlanetNode(String planetNode);
}
