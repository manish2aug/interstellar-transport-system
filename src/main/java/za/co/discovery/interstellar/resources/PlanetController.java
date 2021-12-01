package za.co.discovery.interstellar.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import za.co.discovery.interstellar.model.Ex_400;
import za.co.discovery.interstellar.model.Ex_404;
import za.co.discovery.interstellar.model.Planet;
import za.co.discovery.interstellar.repository.PlanetRepo;
import za.co.discovery.interstellar.model.PlanetRequestBody;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/interstellar")
@Transactional
public class PlanetController {

    private final Logger log = LoggerFactory.getLogger(PlanetController.class);

    @Autowired
    public PlanetRepo planetRepository;

    @GetMapping(value = "/planets")
    public ResponseEntity<List<Planet>> getPlanets() {
        log.debug("PlanetController : getPlanets");
        return ResponseEntity.ok().body(planetRepository.findAll());
    }


    @GetMapping(value = "/planets/{id}")
    public Planet findById(@PathVariable(name = "id") Long id) {
        log.debug("planetController : findById : {}", id);
        return planetRepository.findById(id).orElseThrow(() -> new Ex_404("No planet found for Id = " + id));
    }

    @PostMapping(value = "/planets")
    public ResponseEntity<Planet> createPlanet(@RequestBody PlanetRequestBody planetRequestBody) throws URISyntaxException {
        log.debug("PlanetController : createPlanet : {}", planetRequestBody);
        if (planetRequestBody.getId() != null) {
            throw new Ex_400("A new planet cannot already have an ID ");
        }

        Planet planet = planetRequestBody.getPlanet();
        Planet result = planetRepository.save(planet);
        return ResponseEntity
                .created(new URI("/interstellar/planets/" + result.getId()))
                .body(result);
    }

    @PutMapping(value = "/planets/{id}")
    public ResponseEntity<Planet> updatePlanet(@PathVariable Long id, @RequestBody PlanetRequestBody planetRequestBody) {
        log.debug("PlanetController : updatePlanet : id = {}, planet = {} ", id, planetRequestBody);
        Planet planetDB = planetRepository.findById(id).orElseThrow(() -> new Ex_404("No planet found for Id = " + id));
        Planet planet = planetRequestBody.getPlanet(planetDB);
        return ResponseEntity
                .ok()
                .body(planetRepository.save(planet));
    }

    @DeleteMapping(value = "/planets/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        log.debug("PlanetController : deletePlanet : id = {}", id);
        planetRepository.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }


    @GetMapping("/showPlanets")
    public ModelAndView showAllPlanets() {
        ModelAndView modelAndView = new ModelAndView("planets");
        modelAndView.addObject("planets", planetRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createPlanet() {
        ModelAndView modelAndView = new ModelAndView("addplanet");
        modelAndView.addObject("planet", new Planet());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView updatePlanet(@PathVariable Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView("editplanet");
        modelAndView.addObject("planet", planetRepository.findById(id).get());
        return modelAndView;
    }

    @PostMapping("/save/{id}")
    public ModelAndView updatePlanet(@PathVariable Long id, @ModelAttribute Planet planet, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:/interstellar/showPlanets");
        Optional<Planet> planetDB = planetRepository.findById(id);
        planetDB.get().setPlanetName(planet.getPlanetName());
        planetRepository.save(planetDB.get());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveRoutes(@ModelAttribute Planet planet, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:/interstellar/showPlanets");
        planetRepository.save(planet);
        model.addAttribute("planets", planetRepository.findAll());
        return modelAndView;
    }
}
