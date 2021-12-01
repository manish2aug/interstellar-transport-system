package za.co.discovery.interstellar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    private String planetOrigin;
    private String planetDestination;
    private Double distance;
    private String path;
}
