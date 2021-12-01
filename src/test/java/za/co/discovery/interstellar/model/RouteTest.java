package za.co.discovery.interstellar.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RouteTest {

	@Test
	public void createRoute() {
		Route route = new Route(33,"A","B",Double.parseDouble("0.44"));
		assertEquals("A", route.getPlanetOrigin());
		assertNotNull(route.getRouteId());
	}
	
}
