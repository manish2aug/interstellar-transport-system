package za.co.discovery.interstellar.service;

import interstellar.za.co.discovery.routes.RouteDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.interstellar.utils.ShortestPathUtil;
import za.co.discovery.interstellar.model.RouteDTO;
import za.co.discovery.interstellar.model.Planet;
import za.co.discovery.interstellar.model.Route;
import za.co.discovery.interstellar.repository.PlanetRepo;
import za.co.discovery.interstellar.repository.RoutesRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RoutesRepo routesRepo;

    @Autowired
    private PlanetRepo planetRepository;

    @Autowired
    private ShortestPathUtil shortestPathUtil;

    public List<Route> getRoutes() {
        return routesRepo.findAll();
    }

    /**
     * check if the planets exists
     * @param planetNode
     * @return
     */
    public boolean checkIfPlanetNodeExist(String planetNode) {
        Planet planet = planetRepository.findByPlanetNode(planetNode);
        if (planet != null) {
            return true;
        }
        return false;
    }

    /**
     * Save the route
     * @param route
     * @return
     */
    public Route addRoute(Route route) {
        return routesRepo.save(route);
    }

    /**
     * Calculate the shortest path between the origin and destination
     * @param originPlanet
     * @param destinationPlanet
     * @return
     */
    public RouteDTO calculateShortestPath(String originPlanet, String destinationPlanet) {
        // Get all the routes from the ROUTES table
        List<Route> routes = routesRepo.findAll();

        // Get all the planets from the PLANET_NAMES table
        List<Planet> planets = planetRepository.findAll();

        // Create a HashMap to store the Node. This will then be used for attaching linkages.
        HashMap<String, ShortestPathUtil.Vertex> vertexMap = new HashMap();
        planets.forEach( planet -> {
            vertexMap.put(planet.getPlanetNode(), new ShortestPathUtil.Vertex(planet.getPlanetNode()));
        });

        // Calculate the Shortest Path
        shortestPathUtil.calculateShortestPath(vertexMap, originPlanet, routes);
        ShortestPathUtil.Vertex destination = vertexMap.get(destinationPlanet);
        // Get the Traversal path
        String path = getTraversalPath(destination);
        // Return the Route object
        return new RouteDTO(originPlanet, destinationPlanet, vertexMap.get(destinationPlanet).getDistance(), path);

    }

    /**
     * Get the shortest Traversal path
     * @param destination
     * @return
     */
    private String getTraversalPath(ShortestPathUtil.Vertex destination) {
        StringBuilder builder = new StringBuilder();
        while(destination != null) {
            builder.append(destination.getName()).append(">--");
            destination = previousNode(destination);
        }
        String path = builder.reverse().toString();
        return path;
    }

    /**
     * get the previous predecessor
     * @param predecessor
     * @return
     */
    private ShortestPathUtil.Vertex previousNode(ShortestPathUtil.Vertex predecessor) {
        return predecessor == null ? null : predecessor.getPredecessor();
    }

    /**
     * Calculate the shortest path to all destination planets from source.
     *
     * It also allows you to change the source planet and recalculate the shortest path
     */
    public List<RouteDTO> shortestPathToAllDestinations(String planetOrigin) {
        // Get all the routes from the ROUTES table
        List<Route> routes = routesRepo.findAll();

        // Get all the planets from the PLANET_NAMES table
        List<Planet> planets = planetRepository.findAll();

        // Create a HashMap to store the Node. This will then be used for attaching linkages.
        HashMap<String, ShortestPathUtil.Vertex> vertexMap = new HashMap();
        planets.forEach( planet -> {
            vertexMap.put(planet.getPlanetNode(), new ShortestPathUtil.Vertex(planet.getPlanetNode()));
        });

        // Calculate the Shortest Path
        shortestPathUtil.calculateShortestPath(vertexMap, planetOrigin, routes);

        List<RouteDTO> routeDTOS = new ArrayList<>();
        planets.forEach(planet -> {
            ShortestPathUtil.Vertex destination = vertexMap.get(planet.getPlanetNode());
            routeDTOS.add(new RouteDTO(planetOrigin, planet.getPlanetNode(), destination.getDistance(), getTraversalPath(destination)));
        });
        return routeDTOS;

    }

    /**
     * Calculate the shortest path between the origin and destination using SOAP
     * @param destinationPlanet
     * @return
     */
    public RouteDetails calculateShortestPathUsingSoap(String originPlanet, String destinationPlanet) {
        // Get all the routes from the ROUTES table
        List<Route> routes = routesRepo.findAll();

        // Get all the planets from the PLANET_NAMES table
        List<Planet> planets = planetRepository.findAll();

        // Create a HashMap to store the Node. This will then be used for attaching linkages.
        HashMap<String, ShortestPathUtil.Vertex> vertexMap = new HashMap();
        planets.forEach( planet -> {
            vertexMap.put(planet.getPlanetNode(), new ShortestPathUtil.Vertex(planet.getPlanetNode()));
        });

        // Calculate the Shortest Path
        shortestPathUtil.calculateShortestPath(vertexMap, originPlanet, routes);

        // Return the RouteDetails object
        RouteDetails routeDetails = new RouteDetails();
        routeDetails.setPlanetOrigin(originPlanet);
        routeDetails.setPlanetDestination(destinationPlanet);
        routeDetails.setDistance(vertexMap.get(destinationPlanet).getDistance());
        routeDetails.setPath(getTraversalPath(vertexMap.get(destinationPlanet)));
        return routeDetails;
    }

}
