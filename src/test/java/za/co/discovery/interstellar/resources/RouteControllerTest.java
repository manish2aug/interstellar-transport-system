package za.co.discovery.interstellar.resources;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.interstellar.model.Route;
import za.co.discovery.interstellar.model.RouteRequestBody;
import za.co.discovery.interstellar.service.RouteService;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RouteControllerTest {
	
	private @Mock RouteService routeService;
	private @InjectMocks RoutesController routesController = new RoutesController();
	
	@Test
	public void verifyFindAllRoutesResponse() {
		Route route1 = new Route();
		route1.setRouteId(1);
		route1.setPlanetOrigin("A");
		route1.setPlanetDestination("B");
		route1.setDistance(Double.parseDouble("0.44"));

		Route route2 = new Route();
		route2.setRouteId(2);
		route2.setPlanetOrigin("A");
		route2.setPlanetDestination("C");
		route2.setDistance(Double.parseDouble("0.34"));

		when(routeService.getRoutes()).thenReturn(Lists.list(route1,route2));

		ResponseEntity<List<Route>> routes = routesController.getRoutes();
		assertTrue(routes.getBody().size() > 0);
	}
	
	@Test
	public void verifyFindAllRoutesWithEmptyResponse() {
		when(routeService.getRoutes()).thenReturn(Lists.list());

		ResponseEntity<List<Route>> routes = routesController.getRoutes();
		assertTrue(routes.getBody().size() == 0);
	}
	
	@Test
	public void verifyAddRoutesResponse() throws URISyntaxException {

		RouteRequestBody routeRequestBody = new RouteRequestBody();
		routeRequestBody.setRouteId(33);
		routeRequestBody.setPlanetOrigin("A");
		routeRequestBody.setPlanetDestination("Z");
		routeRequestBody.setDistance(Double.parseDouble("5.44"));

		Route route1 = new Route();
		route1.setRouteId(33);
		route1.setPlanetOrigin("A");
		route1.setPlanetDestination("Z");
		route1.setDistance(Double.parseDouble("5.44"));

		when(routeService.addRoute(route1)).thenReturn(route1);
		ResponseEntity<Route> route = routesController.createRoute(routeRequestBody);

		assertTrue(route.getBody().getPlanetOrigin() == "A");
		assertEquals(201, route.getStatusCodeValue());
	}

}
