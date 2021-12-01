package za.co.discovery.interstellar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLANET_NAMES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    @Id
    @GeneratedValue
    private Long id;
    private String planetNode;
    private String planetName;

    public Planet(String planetNode, String planetName) {
        this.planetNode = planetNode;
        this.planetName = planetName;
    }
}
