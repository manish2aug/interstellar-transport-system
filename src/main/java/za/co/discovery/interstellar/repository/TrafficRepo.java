package za.co.discovery.interstellar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.discovery.interstellar.model.Traffic;

public interface TrafficRepo extends JpaRepository<Traffic, Long> {
}
