package za.co.discovery.interstellar.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.interstellar.model.Route;
import za.co.discovery.interstellar.repository.RoutesRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RouteServiceTest {

	private @Mock
	RoutesRepo routesRepo;
	private @InjectMocks RouteService routesService = new RouteService();
	
	@Test
	public void findAllRoutesReturnsListFromRepo() {
		when(routesRepo.findAll()).thenReturn(new ArrayList<Route>());
		List<Route> results = routesService.getRoutes();
		assertNotNull(results);		
	}
}
