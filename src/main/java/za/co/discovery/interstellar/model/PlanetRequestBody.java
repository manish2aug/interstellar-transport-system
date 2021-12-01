package za.co.discovery.interstellar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetRequestBody {

    private Long id;
    private String planetNode;
    private String planetName;
    private String planetOriginNode;
    private String planetDestinationNode;

    public Planet getPlanet() {
        Planet planet = new Planet();
        planet.setPlanetNode(this.getPlanetNode());
        planet.setPlanetName(this.getPlanetName());
        return planet;
    }

    public Planet getPlanet(Planet planet) {
        if (Objects.nonNull(planet)) {
            planet.setPlanetNode(this.getPlanetNode());
            planet.setPlanetName(this.getPlanetName());
            return planet;
        }
        return null;
    }
}
