package za.co.discovery.interstellar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestBody {
    private int routeId;
    private String planetOrigin;
    private String planetDestination;
    private Double distance;

    public Route getRoute() {
        Route route = new Route();
        route.setRouteId(this.getRouteId());
        route.setDistance(this.getDistance());
        route.setPlanetDestination(this.getPlanetDestination());
        route.setPlanetOrigin(this.getPlanetOrigin());
        return route;
    }

    public Route getRoute(Route route) {
        if (Objects.nonNull(route)) {
            route.setRouteId(this.getRouteId());
            route.setDistance(this.getDistance());
            route.setPlanetDestination(this.getPlanetDestination());
            route.setPlanetOrigin(this.getPlanetOrigin());
            return route;
        }
        return null;
    }
}
