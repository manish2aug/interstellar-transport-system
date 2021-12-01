package za.co.discovery.interstellar.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import za.co.discovery.interstellar.model.Planet;
import za.co.discovery.interstellar.model.Route;
import za.co.discovery.interstellar.model.Traffic;
import za.co.discovery.interstellar.repository.PlanetRepo;
import za.co.discovery.interstellar.repository.RoutesRepo;
import za.co.discovery.interstellar.repository.TrafficRepo;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class DatabaseService {

    private static Logger log = LoggerFactory.getLogger(DatabaseService.class);
    private static final String SHEET_PLANET_NAMES = "Planet Names";
    private static final String SHEET_ROUTES = "Routes";
    private static final String SHEET_TRAFFIC = "Traffic";

    @Autowired
    PlanetRepo planetRepo;

    @Autowired
    RoutesRepo routesRepo;

    @Autowired
    TrafficRepo trafficRepo;


    @PostConstruct
    public void loadData() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new ClassPathResource("data.xlsx").getInputStream());
        log.info("Workbook has " + workbook.getNumberOfSheets() + " sheets.");

        workbook.forEach(sheet -> {
            if (SHEET_PLANET_NAMES.equalsIgnoreCase(sheet.getSheetName())) {
                sheet.forEach(row -> {
                    if (row.getRowNum() != 0) {
                        String planetNode = row.getCell(0).getStringCellValue();
                        String planetName = row.getCell(1).getStringCellValue();
                        Planet planet = new Planet(planetNode, planetName);
                        planetRepo.save(planet);
                    }
                });
            } else if (SHEET_ROUTES.equalsIgnoreCase(sheet.getSheetName())) {
                sheet.forEach(row -> {
                    if (row.getRowNum() != 0) {
                        int routeId = (int) row.getCell(0).getNumericCellValue();
                        String planetOrigin = row.getCell(1).getStringCellValue();
                        String planetDestination = row.getCell(2).getStringCellValue();
                        Double distance = row.getCell(3).getNumericCellValue();
                        Route route = new Route(routeId, planetOrigin, planetDestination, distance);
                        routesRepo.save(route);
                    }
                });
            } else if (SHEET_TRAFFIC.equalsIgnoreCase(sheet.getSheetName())) {
                sheet.forEach(row -> {
                    if (row.getRowNum() != 0) {
                        int routeId = (int) row.getCell(0).getNumericCellValue();
                        String planetOrigin = row.getCell(1).getStringCellValue();
                        String planetDestination = row.getCell(2).getStringCellValue();
                        Double trafficDelay = row.getCell(3).getNumericCellValue();
                        Traffic traffic = new Traffic(routeId, planetOrigin, planetDestination, trafficDelay);
                        trafficRepo.save(traffic);
                    }
                });
            }
        });
        workbook.close();
    }
}
