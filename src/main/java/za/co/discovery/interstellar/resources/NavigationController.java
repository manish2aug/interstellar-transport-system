package za.co.discovery.interstellar.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import za.co.discovery.interstellar.model.PlanetRequestBody;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/interstellar")
@Transactional
public class NavigationController {

    @GetMapping("/rest")
    public ModelAndView calculateShortestPath() {
        ModelAndView modelAndView = new ModelAndView("routesviarest");
        modelAndView.addObject("planetrequest", new PlanetRequestBody());
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView calculateShortestPathWs() {
        ModelAndView modelAndView = new ModelAndView("routesviawebser");
        modelAndView.addObject("planetrequest", new PlanetRequestBody());
        return modelAndView;
    }
}
