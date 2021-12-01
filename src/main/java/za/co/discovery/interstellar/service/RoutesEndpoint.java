package za.co.discovery.interstellar.service;

import interstellar.za.co.discovery.routes.GetRoutesDetailsResponse;
import interstellar.za.co.discovery.routes.GetRoutesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class RoutesEndpoint {

    @Autowired
    RouteService routeService;

    @PayloadRoot(namespace = "http://discovery.co.za.interstellar/routes", localPart = "GetRoutesRequest")
    @ResponsePayload
    public GetRoutesDetailsResponse processRouteDetailsRequest(@RequestPayload GetRoutesRequest request) {
        GetRoutesDetailsResponse response = new GetRoutesDetailsResponse();
        response.setRoutesDetails(routeService.calculateShortestPathUsingSoap(request.getOriginPlanet(), request.getDestinationPlanet()));
        return response;
    }
}
