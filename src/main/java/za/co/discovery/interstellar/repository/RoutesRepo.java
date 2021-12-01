package za.co.discovery.interstellar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.discovery.interstellar.model.Route;

public interface RoutesRepo extends JpaRepository<Route, Long> {
}
