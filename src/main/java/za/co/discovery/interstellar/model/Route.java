package za.co.discovery.interstellar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROUTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    private int routeId;
    private String planetOrigin;
    private String planetDestination;
    private Double distance;
}
