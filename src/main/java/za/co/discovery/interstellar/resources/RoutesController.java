package za.co.discovery.interstellar.resources;

import interstellar.za.co.discovery.routes.GetRoutesRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import za.co.discovery.interstellar.model.Route;
import za.co.discovery.interstellar.repository.RoutesRepo;
import za.co.discovery.interstellar.model.PlanetRequestBody;
import za.co.discovery.interstellar.model.RouteRequestBody;
import za.co.discovery.interstellar.service.RouteService;
import za.co.discovery.interstellar.service.RoutesEndpoint;
import za.co.discovery.interstellar.model.Ex_404;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/interstellar")
@Transactional
public class RoutesController {

    private final Logger log = LoggerFactory.getLogger(RoutesController.class);

    @Autowired
    private RoutesEndpoint routesEndpoint;

    @Autowired
    public RoutesRepo routesRepo;

    @Autowired
    public RouteService routeService;

    /**
     * {@code GET  /Routes} : Get all the routes.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of routes in body.
     */
    @GetMapping(value = "/routes")
    public ResponseEntity<List<Route>> getRoutes() {
        log.debug("RouteController : getRoutes");
        return ResponseEntity.ok().body(routeService.getRoutes());
    }


    /**
     * {@code GET  /routes/:id} : Get the Route based on the "id".
     *
     * @param id the id of the Route to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Route, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(value = "/routes/{id}")
    public Route findById(@PathVariable(name = "id") Long id) {
        log.debug("RouteController : findById : {}", id);
        return routesRepo.findById(id).orElseThrow(() -> new Ex_404("No Route found for Id = " + id));
    }

    /**
     * {@code POST  /routes} : Create a new Route.
     *
     * @param routeRequestBody the Route to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Route, or with status {@code 400 (Bad Request)} if the Route has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/routes")
    public ResponseEntity<Route> createRoute(@RequestBody RouteRequestBody routeRequestBody) throws URISyntaxException {
        log.debug("RouteController : createRoute : {}", routeRequestBody);
        Route Route = routeRequestBody.getRoute();
        Route result = routeService.addRoute(Route);
        return ResponseEntity
                .created(new URI("/interstellar/routes/" + result.getRouteId()))
                .body(result);
    }

    /**
     * {@code PUT  /Routes/:id} : Updates an existing Route.
     *
     * @param id the id of the Route to save.
     * @param RouteRequestBody the Route to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated Route,
     * or with status {@code 404 (Not Found)} if the Route is not found,
     * or with status {@code 500 (Internal Server Error)} if the Route couldn't be updated.
     */
    @PutMapping(value = "/routes/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody RouteRequestBody RouteRequestBody) {
        log.debug("RouteController : updateRoute : id = {}, Route = {} ", id, RouteRequestBody);
        Route RouteDB = routesRepo.findById(id).orElseThrow(() -> new Ex_404("No Route found for Id = " + id));
        Route Route = RouteRequestBody.getRoute(RouteDB);
        return ResponseEntity
                .ok()
                .body(routesRepo.save(Route));
    }

    /**
     * {@code DELETE  /Routes/:id} : Delete the Route based on the "id".
     *
     * @param id the id of the Route to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping(value = "/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        log.debug("RouteController : deleteRoute : id = {}", id);
        routesRepo.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }


    /**
     * Get all the routes
     * @return
     */
    @GetMapping("/routes/showRoutes")
    public ModelAndView showAllRoutes() {
        ModelAndView modelAndView = new ModelAndView("allroutes");
        modelAndView.addObject("routes", routeService.getRoutes());
        return modelAndView;
    }

    /**
     * Get the shortest path
     * @return
     */
    @GetMapping("/routes/path")
    public ModelAndView calculateShortestPathForOrigin() {
        ModelAndView modelAndView = new ModelAndView("routes");
        modelAndView.addObject("planetrequest", new PlanetRequestBody());
        return modelAndView;
    }

    /**
     * To view shortest distance to all the planets from source planet using Soap
     */
    @PostMapping("/routes/shortestPathWs")
    public ModelAndView calculateShortestPathUsingSoap(@ModelAttribute PlanetRequestBody planetRequestBody, Model model) {
        ModelAndView modelAndView = new ModelAndView("shortestpath");
        GetRoutesRequest routesRequest = new GetRoutesRequest();
        routesRequest.setOriginPlanet(planetRequestBody.getPlanetOriginNode());
        routesRequest.setDestinationPlanet(planetRequestBody.getPlanetDestinationNode());
        modelAndView.addObject("route", routesEndpoint.processRouteDetailsRequest(routesRequest).getRoutesDetails());
        return modelAndView;
    }

    /**
     * Create a Route
     * @return
     */
    @GetMapping("/routes/create")
    public ModelAndView createRoutes() {
        ModelAndView modelAndView = new ModelAndView("addroutes");
        modelAndView.addObject("routes", new Route());
        return modelAndView;
    }

    /**
     * Save a Route
     * @param routes
     * @param model
     * @return
     */
    @PostMapping("/routes/save")
    public ModelAndView saveRoutes(@ModelAttribute Route routes, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:/interstellar/routes/showRoutes");
        if(routeService.checkIfPlanetNodeExist(routes.getPlanetOrigin()) && routeService.checkIfPlanetNodeExist(routes.getPlanetDestination())) {
            routeService.addRoute(routes);
            model.addAttribute("routes", routeService.getRoutes());
        } else {
            model.addAttribute("error", "Planet node does not exists");
        }
        return modelAndView;
    }

    /**
     * SHortest path using rest Service
     * @param planetRequestBody
     * @param model
     * @return
     */
    @PostMapping("/routes/shortestPathRs")
    public ModelAndView calculateShortestPathUsingRest(@ModelAttribute PlanetRequestBody planetRequestBody, Model model) {
        ModelAndView modelAndView = new ModelAndView("shortestpath");
        modelAndView.addObject("route", routeService.calculateShortestPath(planetRequestBody.getPlanetOriginNode(), planetRequestBody.getPlanetDestinationNode()));
        return modelAndView;
    }

    /**
     * Shortest Path for any origin
     *
     * @param planetRequestBody
     * @param model
     * @return
     */
    @PostMapping("/routes/shortestPathAll")
    public ModelAndView calculateShortestPathFromPlanetOrigin(@ModelAttribute PlanetRequestBody planetRequestBody, Model model) {
        ModelAndView modelAndView = new ModelAndView("shortestpathallroutes");
        modelAndView.addObject("routes", routeService.shortestPathToAllDestinations(planetRequestBody.getPlanetOriginNode()));
        return modelAndView;
    }
}
